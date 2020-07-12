package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.model.MonthStatistics;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.MonthStatisticsService;
import com.nights.retarded.records.model.RecordsTypeEnum;
import com.nights.retarded.records.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.dao.NoteDao;
import com.nights.retarded.notes.service.NoteService;

@Service("noteService")
public class NoteServiceImpl implements NoteService{

	@Resource(name = "noteDao")
	private NoteDao noteDao;

	@Autowired
    private DayStatisticsService dayStatisticsService;

    @Autowired
    private MonthStatisticsService monthStatisticsService;

	@Autowired
    private RecordService recordService;

	@Override
	public List<Note> getAll() {
		return this.noteDao.findAll();
	}

    @Override
    public Note createNote(String openId, BigDecimal monthBudget) {
        Note note = new Note();
        note.setDayBudget(monthBudget.divide(BigDecimal.valueOf(30), 2, BigDecimal.ROUND_HALF_UP));
        int days = DateUtils.dayToNextMonth(new Date());
        note.setMonthBudget(note.getDayBudget().multiply(BigDecimal.valueOf(days)));
        note.setBalance(note.getMonthBudget());
        note.setDynamicDayBudget(recordService.getDynamicDayBudget(new Date(), note.getBalance()));
        note.setCreateDt(new Date());
        note.setStatus(1);
        note.setOpenId(openId);
        note.setName("默认账本 " + DateUtils.daySdf2.format(new Date()));
        note.setMonthStatisticsState(1);
        note.setDaysWithoutOperation(0);
        noteDao.save(note);
        DayStatistics dayStatistics = dayStatisticsService.initDayStatistics(note);
        dayStatistics.setDynamicDayBudget(null);
        dayStatisticsService.save(dayStatistics);
        return note;
    }

    @Override
    public Note getCurrNote(String openId) {
	    List<Note> list = noteDao.findByOpenIdAndStatusOrderByCreateDtDesc(openId, 1);
	    return JsonUtils.getIndexZero(list);
    }

    @Override
    public List<Note> findByStatus(int i) {
        return noteDao.findByStatus(i);
    }

    @Override
    public void save(Note note) {
        noteDao.save(note);
    }

    @Override
    public Note findById(String noteId) {
        return noteDao.findById(noteId).orElse(null);
    }

    /**
     * 解冻账本，没记账的时间视为日花费=日预算，补充日、月统计数据，更新账本数据
     */
    @Override
    public void unfreeze(Note note) {

        // 读取上一条日统计数据，得知从什么时候冻结的
        DayStatistics lastDayStatistics = dayStatisticsService.findFirstByNoteIdOrderByDtDesc(note.getNoteId());
        Date lastDate = lastDayStatistics.getDt();
        Date now = DateUtils.toDaySdf(new Date());

        // 如果是同一个月，默认没记账的日子里按预算花费
        if(DateUtils.isSameMonth(new Date(), lastDate)){
            spendAccordingToBudget(lastDate, now, note, lastDayStatistics);
            note.setStatus(1);
            noteDao.save(note);
            return;
        }

        // 如果不是同一个月 1. 生成冻结当月的月统计数据（如果没有的话）2. 重置并解冻账本，置月结处理为未处理
        Date monthFirst = DateUtils.monthFirstDay(lastDate);
        Date monthLast = DateUtils.monthLastDay(lastDate);

        // 生成冻结当月的月份统计数据
        MonthStatistics monthStatistics = monthStatisticsService.findByNoteIdAndDt(note.getNoteId(), monthFirst);
        if(monthStatistics == null) {
            createLastMonthStatistics(note, lastDate, monthFirst, monthLast);
        }

        // 更新账本信息
        int days = DateUtils.dayToNextMonth(new Date());
        BigDecimal balance = note.getDayBudget().multiply(BigDecimal.valueOf(days));
        note.setBalance(balance);
        // 设定明天的动态日预算 = 当前余额 / 到月底剩余天数（不算今天的）
        note.setDynamicDayBudget(recordService.getDynamicDayBudget(new Date(), note.getBalance()));
        note.setDaysWithoutOperation(0);
        note.setMonthStatisticsState(0);
        note.setStatus(1);
        noteDao.save(note);

        // 创建今日日统计数据
        DayStatistics dayStatistics = dayStatisticsService.initDayStatistics(note);
        // 今日统计数据 -> 动态日预算 = 昨天的余额 / 到月底剩余天数（算今天的）
        dayStatistics.setDynamicDayBudget(recordService.getDynamicDayBudgetTask(new Date(), note.getBalance()));
        dayStatisticsService.save(dayStatistics);

    }

    /**
     * 创建冻结时的月份数据统计
     * @param note
     * @param lastDate
     * @param monthFirst
     * @param monthLast
     */
    private void createLastMonthStatistics(Note note, Date lastDate, Date monthFirst, Date monthLast) {
        MonthStatistics monthStatistics = statMonthStatistics(note, monthFirst, monthLast);
        monthStatisticsService.save(monthStatistics);
        note.setMonthStatisticsState(0);
    }

    public MonthStatistics statMonthStatistics(Note note, Date monthFirst, Date monthLast) {
        MonthStatistics monthStatistics = new MonthStatistics();
        // 写入上个月的月统计数据
        monthStatistics.setDt(monthFirst);
        // 记账天数
        List<DayStatistics> monthDaysRealList = dayStatisticsService.
                findByNoteIdAndDtGreaterThanEqualAndDtLessThanEqual(note.getNoteId(), monthFirst, monthLast);
        int monthDaysReal = monthDaysRealList.size();
        // 当月预算 = 记账天数 * 日预算
        if(monthFirst.getTime() == DateUtils.currMonthFirstDay().getTime()){
            monthStatistics.setMonthBudget(note.getDayBudget().multiply(BigDecimal.valueOf(DateUtils.monthDays(new Date()))));
        } else {
            monthStatistics.setMonthBudget(note.getDayBudget().multiply(BigDecimal.valueOf(monthDaysReal)));
        }
        // 当月花费 = 当月预算 - 账本余额
        monthStatistics.setMonthSpending(monthStatistics.getMonthBudget().subtract(note.getBalance()));
        // 当月余额 = 当月预算 - 当月花费
        monthStatistics.setBalance(note.getBalance());
        // 平均日花销 = 当月花费 / 记账天数
        monthStatistics.setAvgDaySpending(monthStatistics.getMonthSpending().divide(BigDecimal.valueOf(monthDaysReal), 2, BigDecimal.ROUND_HALF_UP));
        monthStatistics.setNoteId(note.getNoteId());
        // 是否清零，0 将本月超支或省下的钱转结到下月 1 清零
        monthStatistics.setIsClear(1);
        return monthStatistics;
    }

    @Override
    public Note getCurrNoteContainFreeze(String openId) {

        return JsonUtils.getIndexZero(noteDao.findByOpenIdOrderByCreateDtDesc(openId));
    }

    private void spendAccordingToBudget(Date lastDate, Date now, Note note, DayStatistics lastDayStatistics) {

        // 没记账的日子补充日统计数据
        for(Date date = DateUtils.addDay(lastDate, 1); date.getTime() <= now.getTime(); date = DateUtils.addDay(date, 1)){
            DayStatistics dayStatistics = dayStatisticsService.initDayStatistics(note);
            dayStatistics.setDt(date);
            if(date.getTime() == DateUtils.addDay(lastDate, 1).getTime()){
                dayStatistics.setDynamicDayBudget(recordService.getDynamicDayBudget(lastDayStatistics.getDt(), lastDayStatistics.getBalance()));
            } else {
                dayStatistics.setDynamicDayBudget(null);
            }
            dayStatisticsService.save(dayStatistics);
        }
        for(Date date = DateUtils.addDay(lastDate, 1); date.getTime() <= now.getTime(); date = DateUtils.addDay(date, 1)){
            // 把没记账的日子里都填充上系统记账记录
            if(date.getTime() < now.getTime()){
                recordService.addRecord(RecordsTypeEnum.ELSE.getId(),
                        note.getDayBudget(), "冻结期间自动填充", date, note.getNoteId());
            }
        }
    }

}
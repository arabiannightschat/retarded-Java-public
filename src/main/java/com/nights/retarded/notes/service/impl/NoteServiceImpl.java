package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.model.MonthStatistics;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.MonthStatisticsService;
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
        note.setMonthBudget(monthBudget);
        note.setDayBudget(monthBudget.divide(BigDecimal.valueOf(30), 2, BigDecimal.ROUND_HALF_UP));
        int days = DateUtils.dayToNextMonth(new Date());
        note.setBalance(note.getDayBudget().multiply(BigDecimal.valueOf(days)));
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
    public void unfreeze(String noteId, Integer isImportBalance) {

        // 读取上一条日统计数据，得知从什么时候冻结的
        DayStatistics lastDayStatistics = dayStatisticsService.findFirstByNoteIdOrderByDtDesc(noteId);
        Date lastDate = lastDayStatistics.getDt();
        Note note = noteDao.findById(noteId).get();

        // 如果是同一个月，修改账本余额和动态日预算，创建今日日统计数据
        if(DateUtils.isSameMonth(new Date(), lastDate)){
            note = sameMonthUnfreeze(isImportBalance, lastDate, note);

        } else { // 如果不是同一个月 TODO

            // 生成冻结当月的月统计数据（如果没有的话）
            // 根据是否继承余额，更新账本信息
            // 创建今日统计数据
        }

        // 保存账本数据
        note.setStatus(1);
        note.setDaysWithoutOperation(0);
        noteDao.save(note);

        // 创建今日日统计数据
        DayStatistics dayStatistics = dayStatisticsService.initDayStatistics(note);
        // 今日统计数据 -> 动态日预算 = 昨天的余额 / 到月底剩余天数（算今天的）
        dayStatistics.setDynamicDayBudget(recordService.getDynamicDayBudgetTask(new Date(), note.getBalance()));
        dayStatisticsService.save(dayStatistics);

    }

    private Note sameMonthUnfreeze(Integer isImportBalance, Date lastDate, Note note) {
        Date startDt = DateUtils.addDay(lastDate,1);
        Date endDt = DateUtils.toDaySdf(new Date());
        int days = DateUtils.diff(startDt, endDt);
        // 如果继承余额
        if(isImportBalance == 1) {
            // 设定账本：当前余额 = 之前的余额 - 日花费 * 没记账的天数
            note.setBalance(note.getBalance().subtract(note.getDayBudget().multiply(BigDecimal.valueOf(days))));
        } else { // 如果不继承余额
            int dayToNextMonth = DateUtils.dayToNextMonth(DateUtils.currMonthFirstDay());
            note.setBalance(note.getDayBudget().multiply(BigDecimal.valueOf(dayToNextMonth)));
        }
        // 设定明天的动态日预算 = 当前余额 / 到月底剩余天数（不算今天的）
        note.setDynamicDayBudget(recordService.getDynamicDayBudget(new Date(), note.getBalance()));
        return note;
    }

}
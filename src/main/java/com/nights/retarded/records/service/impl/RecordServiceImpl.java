package com.nights.retarded.records.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.StringUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.NoteService;
import com.nights.retarded.records.model.RecentRecords;
import com.nights.retarded.records.model.RecordVO;
import com.nights.retarded.records.model.RecordsType;
import com.nights.retarded.records.service.RecordsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nights.retarded.records.model.Record;
import com.nights.retarded.records.dao.RecordDao;
import com.nights.retarded.records.service.RecordService;

@Service("recordService")
public class RecordServiceImpl implements RecordService{

	@Resource(name = "recordDao")
	private RecordDao recordDao;

	@Autowired
    private RecordsTypeService recordsTypeService;

	private static final int recentDays = 10;

	@Autowired
    private NoteService noteService;

	@Autowired
    private DayStatisticsService dayStatisticsService;

	@Override
	public List<Record> getAll() {
		return this.recordDao.findAll();
	}

    @Override
    public List<RecentRecords> getRecentRecords(Note note) {
        if(note == null) {
            return null;
        }
        Date now = DateUtils.toDaySdf(new Date());
        Date startTime = DateUtils.addDay(now, (-1) * recentDays);
        return getRecentRecords(note.getNoteId(), startTime, now);
    }

    @Override
    public Map recordsLoading(int recordsLoadingCount, String currNoteId) {

        Map<String, Object> result = new HashMap<>();
        if(recordsLoadingCount > 20){
            result.put("tooLong", true);
        }
        if(StringUtils.isBlank(currNoteId)){
            return null;
        }
        Date now = DateUtils.toDaySdf(new Date());
        Date endTime = DateUtils.addDay(now, (recordsLoadingCount * (-1) * recentDays) - 1);
        Date startTime = DateUtils.addDay(endTime, (-1) * recentDays);
        List<RecentRecords> list = getRecentRecords(currNoteId, startTime, endTime);
        if(list.size() == 0) {
            int count = recordDao.countByNoteIdAndDtLessThanEqual(currNoteId, startTime);
            if(count == 0) {
                result.put("noMore", true);
                return result;
            } else {
                return recordsLoading(recordsLoadingCount + 1, currNoteId);
            }
        }
        result.put("list", list);
        result.put("recordsLoadingCount", recordsLoadingCount);
        return result;
    }

    private List<RecentRecords> getRecentRecords(String noteId, Date startTime, Date endTime) {
        List<Record> list = recordDao.findByNoteIdAndDtBetweenOrderByDtDesc(noteId, startTime, endTime);

        Map<String, RecordsType> typesMap = new HashMap<>();

        List<RecentRecords> result = new ArrayList<>();
        List<RecordVO> recordsTemp = new ArrayList<>();
        BigDecimal totalSpendingTemp = BigDecimal.valueOf(0);
        RecentRecords recentRecords = new RecentRecords();
        for(int i = 0;i < list.size() ;i++){
            Record record = list.get(i);
            if(i == 0 || record.getDt().getTime() < list.get(i-1).getDt().getTime()){
                if(i != 0){
                    recentRecords.setRecords(recordsTemp);
                    recentRecords.setTotalSpending(totalSpendingTemp);
                    result.add(recentRecords);
                }
                recentRecords = new RecentRecords();
                recordsTemp = new ArrayList<>();
                totalSpendingTemp = BigDecimal.valueOf(0);

                recentRecords.setDt(record.getDt());
                SimpleDateFormat sdf = new SimpleDateFormat("M月dd日 EEEE");
                String date = sdf.format(record.getDt());
                recentRecords.setDate(date);
            }
            RecordVO recordVO = recordToRecordVO(typesMap, record);
            recordsTemp.add(recordVO);
            totalSpendingTemp = totalSpendingTemp.add(record.getMoney()).setScale(2, BigDecimal.ROUND_UP);
        }
        if(list.size() != 0){
            recentRecords.setRecords(recordsTemp);
            recentRecords.setTotalSpending(totalSpendingTemp);
            result.add(recentRecords);
        }
        return result;
    }

    private RecordVO recordToRecordVO(Map<String, RecordsType> typesMap, Record record) {
        RecordVO recordVO = new RecordVO();
        RecordsType recordsType = typesMap.get(record.getTypeId());
        if(recordsType == null) {
            recordsType = recordsTypeService.findById(record.getTypeId());
            typesMap.put("record.getTypeId()",recordsType);
        }
        if(StringUtils.isNotBlank(record.getDescription())){
            recordVO.setDescription(record.getDescription());
        } else {
            recordVO.setDescription(recordsType.getName());
        }
        recordVO.setIcon(recordsType.getIcon());
        if(record.getMoney() != null){
            recordVO.setMoney(record.getMoney().setScale(2, BigDecimal.ROUND_UP));
        }
        if(new Date().getTime() - record.getCreateDt().getTime() <= 1000*60*30){
            recordVO.setNew(true);
        } else {
            recordVO.setNew(false);
        }
        recordVO.setRecordId(record.getRecordId());
        return recordVO;
    }

    @Override
    public void addRecord(String recordTypeId, BigDecimal money, String description, Date dt, String noteId) {

	    // 格式化时间
	    dt = DateUtils.toDaySdf(dt);
	    // 带符号支出金额
        RecordsType type = recordsTypeService.findById(recordTypeId);
        Note note = noteService.findById(noteId);
        BigDecimal moneySign = money.multiply(BigDecimal.valueOf(type.getType() == 0 ? 1 : -1));
        Record record = packageRecord(recordTypeId, money, description, dt, type, note);
        recordDao.save(record);
        editDayStatAndNote(dt, note, moneySign);
    }

    @Override
    public void delRecord(String recordId) {
        Record record = recordDao.findById(recordId).orElse(null);
        Note note = noteService.findById(record.getNoteId());
        RecordsType type = recordsTypeService.findById(record.getTypeId());
        BigDecimal moneySign = record.getMoney().multiply(BigDecimal.valueOf(type.getType() == 0 ? -1 : 1));
        editDayStatAndNote(record.getDt(), note, moneySign);
        recordDao.deleteById(recordId);
    }

    private void editDayStatAndNote(Date dt, Note note, BigDecimal moneySign) {

	    // 如果是当月记账，更新往后的日统计数据，更新账本余额和动态日预算即可
        int nowMonth = DateUtils.getMonth(new Date());
        int recordMonth = DateUtils.getMonth(dt);
        if(nowMonth == recordMonth) {

            // TODO

        } else {
            // 如果不是当月记账，需处理月统计数据，且根据月统计是否转结决定后面的日统计数据要不要改变 TODO


        }

        // 更新日统计数据 ( 指定日期后的数据都会受到影响 )
        BigDecimal nextDynamicDayBudget = null;
        List<DayStatistics> dayStatisticsList = dayStatisticsService.findByNoteIdAndDtGreaterThanEqualOrderByDtAsc(note.getNoteId(), dt);
        for(int i = 0; i < dayStatisticsList.size() ; i++){
            DayStatistics dayStatistics = dayStatisticsList.get(i);
            if(dayStatistics.getDt().getTime() == dt.getTime()){
                dayStatistics.setDaySpending(dayStatistics.getDaySpending().add(moneySign));
            }
            dayStatistics.setBalance(dayStatistics.getBalance().subtract(moneySign));
            if(i > 0){
                dayStatistics.setDynamicDayBudget(nextDynamicDayBudget);
            }
            nextDynamicDayBudget = getDynamicDayBudget(dayStatistics.getDt(), dayStatistics.getBalance());
        }

        // 计算账本余额数据
        note.setBalance(note.getBalance().subtract(moneySign));
        note.setDynamicDayBudget(nextDynamicDayBudget);

        noteService.save(note);
        dayStatisticsService.saveAll(dayStatisticsList);
    }

    private Record packageRecord(String recordTypeId, BigDecimal money, String description, Date dt, RecordsType type, Note note) {
        Record record = new Record();
        record.setCreateDt(new Date());
        if(StringUtils.isNotBlank(description)){
            record.setDescription(description);
        } else {
            record.setDescription(type.getName());
        }
        record.setDt(dt);
        record.setMoney(money);
        record.setTypeId(recordTypeId);
        record.setNoteId(note.getNoteId());
        return record;
    }

    // 动态日预算 = 余额 / 本月剩余天数; 日预算 = 月预算 / 30
    // （不算今天的）
    public BigDecimal getDynamicDayBudget(Date dt, BigDecimal balance){
        int dayToNextMonth = DateUtils.dayToNextMonth(dt) - 1;
        if(dayToNextMonth == 0) {
            return null;
        }
        return balance.divide(BigDecimal.valueOf(dayToNextMonth), 2, BigDecimal.ROUND_HALF_UP);
    }

    //（算今天的）
    @Override
    public BigDecimal getDynamicDayBudgetTask(Date date, BigDecimal balance) {
        int dayToNextMonth = DateUtils.dayToNextMonth(date);
        if(dayToNextMonth == 0) {
            return null;
        }
        return balance.divide(BigDecimal.valueOf(dayToNextMonth), 2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public int countByNoteIdAndDt(String noteId, Date yesterday) {
        return recordDao.countByNoteIdAndDt(noteId, yesterday);
    }

}
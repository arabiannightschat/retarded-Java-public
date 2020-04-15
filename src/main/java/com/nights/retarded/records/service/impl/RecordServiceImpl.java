package com.nights.retarded.records.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.StringUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.NoteService;
import com.nights.retarded.records.model.RecentRecords;
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

	@Autowired
    private NoteService noteService;

	@Autowired
    private DayStatisticsService dayStatisticsService;

	@Override
	public List<Record> getAll() {
		return this.recordDao.findAll();
	}

    @Override
    public List<RecentRecords> getRecentRecords(String openId) {
        Note note = noteService.getCurrNote(openId);
        Date now = new Date();
        now = DateUtils.toDaySdf(now);
        Date startTime = DateUtils.addDay(now, -5);
        List<Record> list = recordDao.findByNoteIdAndDtBetweenOrderByCreateDtDesc(note.getNoteId(), now, startTime);

        List<RecentRecords> result = new ArrayList<>();
        List<Record> recordsTemp = new ArrayList<>();
        BigDecimal totalSpendingTemp = BigDecimal.valueOf(0);
        RecentRecords recentRecords = new RecentRecords();
        for(int i = 0;i < list.size() ;i++){
            Record record = list.get(i);
            if(i == 0 || record.getDt().getTime() < list.get(i-1).getDt().getTime()){
                if(i != 0){
                    result.add(recentRecords);
                } else {
                    recentRecords = new RecentRecords();
                    recordsTemp = new ArrayList<>();
                    totalSpendingTemp = BigDecimal.valueOf(0);
                }
                recentRecords.setDt(record.getDt());
                SimpleDateFormat sdf = new SimpleDateFormat("M月dd日 EEEE");
                String date = sdf.format(record.getDt());
                recentRecords.setDate(date);
            }
            recordsTemp.add(record);
            totalSpendingTemp = totalSpendingTemp.add(record.getMoney());
        }

        result.add(recentRecords);
        return result;
    }

    @Override
    public void addRecord(String recordTypeId, BigDecimal money, String description, Date dt, String openId) {
	    dt = DateUtils.toDaySdf(dt);
        RecordsType type = recordsTypeService.findById(recordTypeId);
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
        Note note = noteService.getCurrNote(openId);
        record.setNoteId(note.getNoteId());
        recordDao.save(record);

        // 更新日统计数据
        DayStatistics dayStatistics = dayStatisticsService.findByNoteIdAndDt(note.getNoteId(), dt);
        BigDecimal moneySign = money.multiply(BigDecimal.valueOf(type.getType() == 0 ? 1 : -1));
        if(dayStatistics == null) {
            dayStatistics = new DayStatistics();
            dayStatistics.setDt(dt);
            dayStatistics.setDaySpending(moneySign);
            dayStatistics.setNoteId(note.getNoteId());
            dayStatistics.setDayBudget(note.getDayBudget());
            dayStatistics.setDynamicDayBudget(note.getDynamicDayBudget());
        } else {
            dayStatistics.setDaySpending(dayStatistics.getDaySpending().add(moneySign));
        }

        dayStatisticsService.save(dayStatistics);

        // 计算账本余额数据
        if(type.getType() == 0){ // 支出
            note.setBalance(note.getBalance().subtract(money));
        } else {
            note.setBalance(note.getBalance().add(money));
        }

        // 动态日预算 = 余额 / 本月剩余天数; 日预算 = 月预算 / 30
        int dayToNextMonth = DateUtils.dayToNextMonth(new Date());
        note.setDynamicDayBudget(note.getBalance().divide(BigDecimal.valueOf(dayToNextMonth), 2, BigDecimal.ROUND_HALF_UP));

        noteService.save(note);

    }

}
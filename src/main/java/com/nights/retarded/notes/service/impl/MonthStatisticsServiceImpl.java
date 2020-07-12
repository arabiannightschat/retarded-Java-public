package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.NoteService;
import com.nights.retarded.records.model.RecordsTypeEnum;
import com.nights.retarded.records.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nights.retarded.notes.model.MonthStatistics;
import com.nights.retarded.notes.dao.MonthStatisticsDao;
import com.nights.retarded.notes.service.MonthStatisticsService;

@Service("monthStatisticsService")
public class MonthStatisticsServiceImpl implements MonthStatisticsService{

	@Resource(name = "monthStatisticsDao")
	private MonthStatisticsDao monthStatisticsDao;

	@Autowired
    private NoteService noteService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private DayStatisticsService dayStatisticsService;

	@Override
	public List<MonthStatistics> getAll() {
		return this.monthStatisticsDao.findAll();
	}

    @Override
    public void save(MonthStatistics monthStatistics) {
        monthStatisticsDao.save(monthStatistics);
    }

    @Override
    public MonthStatistics getLastMonthStatistics(String noteId) {
        return JsonUtils.getIndexZero(monthStatisticsDao.findByNoteIdOrderByDtDesc(noteId));
    }

    @Override
    public void importLastMonthBalance(String noteId, Integer isImport) {
        Note note = noteService.findById(noteId);
	    if(isImport == 1){
	        // 如果需要引入上月结余或欠款，修改账本数据
            MonthStatistics monthStatistics = getLastMonthStatistics(noteId);
            monthStatistics.setIsClear(0);
            monthStatisticsDao.save(monthStatistics);
            int month = DateUtils.getMonth(DateUtils.monthFirstDay(monthStatistics.getDt()));
            recordService.addRecord(RecordsTypeEnum.SETTLE.getId(), monthStatistics.getBalance(),
                    month + "月余额结转", DateUtils.monthFirstDay(new Date()), noteId);

        }
        // 标记为已处理
        note.setMonthStatisticsState(1);
        noteService.save(note);

    }

    @Override
    public MonthStatistics findByNoteIdAndDt(String noteId, Date monthFirst) {
        return monthStatisticsDao.findByNoteIdAndDt(noteId, monthFirst);
    }

    @Override
    public Map<String, Object> getMonthStatistics(Date monthDate, String currNoteId) {

        Date monthFirstDay = DateUtils.monthFirstDay(monthDate);
        Date monthLastDay = DateUtils.monthLastDay(monthDate);
        Map<String, Object> result = new HashMap<>();
        Note note = noteService.findById(currNoteId);

        // 获取月份统计数据
        MonthStatistics monthStatistics = monthStatisticsDao.findByNoteIdAndDt(currNoteId, monthFirstDay);

        if(monthStatistics == null) {
            monthStatistics = noteService.statMonthStatistics(noteService.findById(currNoteId), monthFirstDay, monthLastDay);
        }
        result.put("monthStatistics", monthStatistics);

        // 获取日统计数据列表
        List<DayStatistics> dayStatisticsList = dayStatisticsService.findByNoteIdAndDtGreaterThanEqualAndDtLessThanEqual(
                currNoteId, monthFirstDay, monthLastDay);
        // 获取图表信息
        List<String> categories = new ArrayList<>();
        List<BigDecimal> daySpending = new ArrayList<>();
        List<BigDecimal> dayBudget = new ArrayList<>();
        for(DayStatistics dayStatistics : dayStatisticsList) {
            String date = DateUtils.toCategoriesDay(dayStatistics.getDt());
            categories.add(date);
            daySpending.add(dayStatistics.getDaySpending());
            dayBudget.add(dayStatistics.getDayBudget());
        }
        result.put("categories", categories);
        result.put("daySpending", daySpending);
        result.put("dayBudget", dayBudget);

        // 获取记账类型分布饼图
        List<Map<String,Object>> ringChartsData = monthStatisticsDao.statSumByType(currNoteId, monthFirstDay, monthLastDay);
        result.put("ringChartsData", ringChartsData);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月");
        result.put("currMonth", sdf.format(new Date()));
        result.put("startMonth", note.getCreateDt());

        return result;
    }


}
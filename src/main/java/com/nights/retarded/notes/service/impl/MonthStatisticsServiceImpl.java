package com.nights.retarded.notes.service.impl;

import com.nights.retarded.notes.dao.MonthStatisticsDao;
import com.nights.retarded.notes.model.entity.DayStatistics;
import com.nights.retarded.notes.model.entity.MonthStatistics;
import com.nights.retarded.notes.model.entity.Note;
import com.nights.retarded.notes.model.vo.StatisticsLineChart;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.MonthStatisticsService;
import com.nights.retarded.notes.service.NoteService;
import com.nights.retarded.records.model.enums.RecordsTypeEnum;
import com.nights.retarded.records.service.RecordService;
import com.nights.retarded.utils.DateUtils;
import com.nights.retarded.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("monthStatisticsService")
public class MonthStatisticsServiceImpl implements MonthStatisticsService{

	@Autowired
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
            int month = DateUtils.getMonth(DateUtils.monthBegin(monthStatistics.getDt()));
            recordService.addRecord(RecordsTypeEnum.SETTLE.getId(), monthStatistics.getBalance().multiply(BigDecimal.valueOf( -1)),
                    month + "月余额结转", DateUtils.monthBegin(new Date()), noteId);
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

        Date monthFirstDay = DateUtils.monthBegin(monthDate);
        Date monthLastDay = DateUtils.monthEnd(monthDate);
        Map<String, Object> result = new HashMap<>();
        Note note = noteService.findById(currNoteId);

        // 获取月份统计数据
        MonthStatistics monthStatistics = monthStatisticsDao.findByNoteIdAndDt(currNoteId, monthFirstDay);
        if(monthStatistics == null) {
            monthStatistics = noteService.statMonthStatistics(noteService.findById(currNoteId), monthFirstDay, monthLastDay);
        }
        result.put("monthStatistics", monthStatistics);

        // 获取日统计数据折线图
        List<DayStatistics> dayStatisticsList = dayStatisticsService.findByNoteIdAndDtGreaterThanEqualAndDtLessThanEqual(
                currNoteId, monthFirstDay, monthLastDay);
        StatisticsLineChart statisticsLineChart = new StatisticsLineChart();
        for(DayStatistics dayStatistics : dayStatisticsList) {
            String date = DateUtils.toCategoriesDay(dayStatistics.getDt());
            statisticsLineChart.getCategories().add(date);
            statisticsLineChart.getDaySpending().add(dayStatistics.getDaySpending());
            statisticsLineChart.getDayBudget().add(dayStatistics.getDayBudget());
        }
        result.put("lineChartData", statisticsLineChart);

        // 获取记账类型分布饼图
        List<Map<String,Object>> ringChartsData = monthStatisticsDao.statSumByType(currNoteId, monthFirstDay, monthLastDay);
        result.put("ringChartsData", ringChartsData);

        result.put("startMonth", note.getCreateDt());

        return result;
    }


}
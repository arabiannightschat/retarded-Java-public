package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.NoteService;
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
        return monthStatisticsDao.findByNoteIdAndDt(noteId, DateUtils.lastMonthFirstDay());
    }

    @Override
    public void importLastMonthBalance(String noteId, Integer isImport) {
        Note note = noteService.findById(noteId);
	    if(isImport == 1){
	        // 如果需要引入上月结余或欠款，修改账本数据
            MonthStatistics monthStatistics = getLastMonthStatistics(noteId);
            monthStatistics.setIsClear(0);
            monthStatisticsDao.save(monthStatistics);
            note.setBalance(note.getBalance().add(monthStatistics.getBalance()));
            note.setDynamicDayBudget(recordService.getDynamicDayBudget(new Date(), note.getBalance()));
            // 处理本月已生成的日统计数据，将上月结余或欠款加上，重新计算余额和日动态预算
            List<DayStatistics> dayStatisticsList = dayStatisticsService.
                        findByNoteIdAndDtGreaterThanEqualOrderByDtAsc(note.getNoteId(), DateUtils.currMonthFirstDay());
            BigDecimal nextDynamicDayBudget = null;
            for(int i = 0; i < dayStatisticsList.size() ; i++){
                DayStatistics dayStatistics = dayStatisticsList.get(i);
                dayStatistics.setBalance(dayStatistics.getBalance().add(monthStatistics.getBalance()));
                if(i > 0){
                    dayStatistics.setDynamicDayBudget(nextDynamicDayBudget);
                }
                nextDynamicDayBudget = recordService.getDynamicDayBudget(dayStatistics.getDt(), dayStatistics.getBalance());
                dayStatisticsService.save(dayStatistics);
            }
        }
        // 标记为已处理
        note.setMonthStatisticsState(1);
        noteService.save(note);

    }

    @Override
    public MonthStatistics findByNoteIdAndDt(String noteId, Date monthFirst) {
        return monthStatisticsDao.findByNoteIdAndDt(noteId, monthFirst);
    }

}
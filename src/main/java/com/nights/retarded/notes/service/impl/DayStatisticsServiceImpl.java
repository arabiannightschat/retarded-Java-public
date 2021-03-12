package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.nights.retarded.notes.model.vo.recentData.ChartsData;
import com.nights.retarded.notes.model.vo.recentData.RecentData;
import com.nights.retarded.notes.model.vo.recentData.SimpleData;
import com.nights.retarded.utils.DateUtils;
import com.nights.retarded.utils.JsonUtils;
import com.nights.retarded.notes.model.entity.Note;
import com.nights.retarded.notes.service.NoteService;
import com.nights.retarded.records.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nights.retarded.notes.model.entity.DayStatistics;
import com.nights.retarded.notes.dao.DayStatisticsDao;
import com.nights.retarded.notes.service.DayStatisticsService;

@Service("dayStatisticsService")
public class DayStatisticsServiceImpl implements DayStatisticsService{

	@Autowired
	private DayStatisticsDao dayStatisticsDao;

	@Autowired
    private NoteService noteService;

	@Autowired
    private RecordService recordService;

	@Override
	public List<DayStatistics> getAll() {
		return this.dayStatisticsDao.findAll();
	}

    @Override
    public RecentData getRecentData(String openId) {
        RecentData recentData = new RecentData();
	    // 获取基本信息
        Note note = noteService.getCurrNoteContainFreeze(openId);
        if(note == null) {
            return null;
        }
        if(note.getStatus() == 0) {
            recentData.setNote(note);
            return recentData;
        }
        Date now = new Date();
        now = DateUtils.toDaySdf(now);
        Date startTime = DateUtils.addDay(now, -5);
        List<DayStatistics> list = dayStatisticsDao.findByNoteIdAndDtBetweenOrderByDtAsc(note.getNoteId(), startTime, now);

        recentData.setSimpleData(new SimpleData(note.getBalance(), DateUtils.dayToNextMonth(now),
                DateUtils.getYear(now), DateUtils.getMonth(now)));

        // 获取图表信息
        ChartsData chartsData = new ChartsData();
        for(DayStatistics dayStatistics : list) {
            String date = DateUtils.toCategories(dayStatistics.getDt());
            chartsData.getCategories().add(date);
            chartsData.getDaySpending().add(dayStatistics.getDaySpending());
            chartsData.getDayBudget().add(dayStatistics.getDayBudget());
            chartsData.getDynamicDayBudget().add(dayStatistics.getDynamicDayBudget());
        }
        if(chartsData.getCategories().size() > 0){
            for(int i = 0; i < 2 ; i++){
                chartsData.getCategories().add(DateUtils.toCategories(DateUtils.addDay(now, i+1)));
            }
            chartsData.getDayBudget().add(note.getDayBudget());
            chartsData.getDynamicDayBudget().add(note.getDynamicDayBudget());
        }
        recentData.setChartsData(chartsData);
        recentData.setNote(note);
        recentData.setRecentRecords(recordService.getRecentRecords(note));

        return recentData;
    }

    @Override
    public DayStatistics findByNoteIdAndDt(String noteId, Date dt) {
        return dayStatisticsDao.findByNoteIdAndDt(noteId, dt);
    }

    @Override
    public void save(DayStatistics dayStatistics) {
        dayStatisticsDao.save(dayStatistics);
    }

    @Override
    public void saveAll(List<DayStatistics> dayStatisticsList) {
        dayStatisticsDao.saveAll(dayStatisticsList);
    }

    @Override
    public DayStatistics initDayStatistics(Note note) {
        DayStatistics dayStatistics = new DayStatistics();
        Date today = DateUtils.toDaySdf(new Date());
        dayStatistics.setDt(today);
        dayStatistics.setDaySpending(BigDecimal.ZERO);
        dayStatistics.setNoteId(note.getNoteId());
        dayStatistics.setDayBudget(note.getDayBudget());
        dayStatistics.setDynamicDayBudget(note.getDynamicDayBudget());
        dayStatistics.setBalance(note.getBalance());
        dayStatisticsDao.save(dayStatistics);
        return dayStatistics;
    }

    @Override
    public List<DayStatistics> findByNoteIdAndDtGreaterThanEqualOrderByDtAsc(String noteId, Date dt) {
        return dayStatisticsDao.findByNoteIdAndDtGreaterThanEqualOrderByDtAsc(noteId, dt);
    }

    @Override
    public DayStatistics findFirstByNoteIdOrderByDtDesc(String noteId) {
        return JsonUtils.getIndexZero(dayStatisticsDao.findByNoteIdOrderByDtDesc(noteId));
    }

    @Override
    public void deleteLastDaysData(String noteId, int freezeDaysWithoutOperation) {
        Date startDt = DateUtils.addDay(DateUtils.toDaySdf(new Date()), freezeDaysWithoutOperation*(-1) );
        List<DayStatistics> list = dayStatisticsDao.findByNoteIdAndDtGreaterThanEqualOrderByDtAsc(noteId, startDt);
        dayStatisticsDao.deleteAll(list);
    }

    @Override
    public List<DayStatistics> findByNoteIdAndDtGreaterThanEqualAndDtLessThanEqual(String noteId, Date monthFirst, Date monthLast) {
        return dayStatisticsDao.findByNoteIdAndDtGreaterThanEqualAndDtLessThanEqualOrderByDtAsc(noteId, monthFirst, monthLast);
    }


}
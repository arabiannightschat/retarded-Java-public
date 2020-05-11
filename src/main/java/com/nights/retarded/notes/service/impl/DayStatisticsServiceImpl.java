package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.dao.DayStatisticsDao;
import com.nights.retarded.notes.service.DayStatisticsService;

@Service("dayStatisticsService")
public class DayStatisticsServiceImpl implements DayStatisticsService{

	@Resource(name = "dayStatisticsDao")
	private DayStatisticsDao dayStatisticsDao;

	@Autowired
    private NoteService noteService;

	@Override
	public List<DayStatistics> getAll() {
		return this.dayStatisticsDao.findAll();
	}

    @Override
    public Map getRecentData(String openId) {
	    // 获取基本信息
        Note note = noteService.getCurrNote(openId);
        if(note == null) {
            return null;
        }
        Date now = new Date();
        now = DateUtils.toDaySdf(now);
        Date startTime = DateUtils.addDay(now, -5);
        List<DayStatistics> list = dayStatisticsDao.findByNoteIdAndDtBetweenOrderByDtAsc(note.getNoteId(), startTime, now);
        Map<String, Object> map = new HashMap<>();
        map.put("balance", note.getBalance());
        map.put("dayToNextMonth",DateUtils.dayToNextMonth(new Date()));
        map.put("year", DateUtils.getYear(new Date()));
        map.put("month", DateUtils.getMonth(new Date()));

        // 获取图表信息
        List<String> categories = new ArrayList<>();
        List<BigDecimal> daySpending = new ArrayList<>();
        List<BigDecimal> dayBudget = new ArrayList<>();
        List<BigDecimal> dynamicDayBudget = new ArrayList<>();
        for(DayStatistics dayStatistics : list) {
            String date = DateUtils.toCategories(dayStatistics.getDt());
            categories.add(date);
            daySpending.add(dayStatistics.getDaySpending());
            dayBudget.add(dayStatistics.getDayBudget());
            dynamicDayBudget.add(dayStatistics.getDynamicDayBudget());
        }
        if(categories.size() > 0){
            for(int i = 0; i < 2 ; i++){
                categories.add(DateUtils.toCategories(DateUtils.addDay(now, i+1)));
            }
            dayBudget.add(note.getDayBudget());
            dynamicDayBudget.add(note.getDynamicDayBudget());
        }
        map.put("categories", categories);
        map.put("daySpending", daySpending);
        map.put("dayBudget", dayBudget);
        map.put("dynamicDayBudget", dynamicDayBudget);
        map.put("note", note);
        return map;
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
        return dayStatisticsDao.findByNoteIdAndDtGreaterThanEqualAndDtLessThanEqual(noteId, monthFirst, monthLast);
    }


}
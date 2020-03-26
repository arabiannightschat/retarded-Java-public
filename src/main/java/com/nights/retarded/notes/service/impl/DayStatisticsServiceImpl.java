package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
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
        Note note = noteService.getCurrNote(openId);
        if(note == null) {
            return null;
        }
        Date now = new Date();
        now = DateUtils.toDaySdf(now);
        Date startTime = DateUtils.addDay(now, -5);
        List<DayStatistics> list = dayStatisticsDao.findByNoteIdAndDtBetween(note.getNoteId(), now, startTime);
        Map<String, Object> map = new HashMap<>();
        map.put("balance", note.getBalance());
        map.put("dayToNextMonth",DateUtils.dayToNextMonth(new Date()));
        map.put("year", DateUtils.getYear(new Date()));
        map.put("month", DateUtils.getMonth(new Date()));
        map.put("list", list);
        List<String> categories = new ArrayList<>();
        List<BigDecimal> daySpending = new ArrayList<>();
        List<BigDecimal> dayBudget = new ArrayList<>();
        List<BigDecimal> dynamicDayBudget = new ArrayList<>();
        BigDecimal lastDynamicDayBudget = null;
        BigDecimal lastDayBudget = null;
        for(DayStatistics dayStatistics : list) {
            String date = DateUtils.toCategories(dayStatistics.getDt());
            categories.add(date);
            daySpending.add(dayStatistics.getDaySpending());
            dayBudget.add(dayStatistics.getDayBudget());
            dynamicDayBudget.add(dayStatistics.getDynamicDayBudget());
            lastDayBudget = dayStatistics.getDayBudget();
            lastDynamicDayBudget = dayStatistics.getDynamicDayBudget();
        }
        if(categories.size() > 0){
            for(int i = 0; i < 2 ; i++){
                categories.add(DateUtils.toCategories(DateUtils.addDay(now, i+1)));
                dynamicDayBudget.add(lastDynamicDayBudget);
                dayBudget.add(lastDayBudget);
            }
        }
        map.put("categories", categories);
        map.put("daySpending", categories);
        map.put("dayBudget", categories);
        map.put("dynamicDayBudget", categories);
        return map;
    }


}
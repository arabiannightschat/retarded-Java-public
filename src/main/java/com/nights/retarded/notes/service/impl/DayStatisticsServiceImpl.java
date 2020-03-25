package com.nights.retarded.notes.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Date now = new Date();
        now = DateUtils.toDaySdf(now);
        Date startTime = DateUtils.addDay(now, -5);
        List<DayStatistics> list = dayStatisticsDao.findByNoteIdAndDtBetween(note.getNoteId(), now, startTime);
        Map<String, Object> map = new HashMap<>();
        map.put("balance", note.getBalance());
        map.put("dayToNextMonth",DateUtils.dayToNextMonth(new Date()));
        map.put("list", list);
        return map;
    }


}
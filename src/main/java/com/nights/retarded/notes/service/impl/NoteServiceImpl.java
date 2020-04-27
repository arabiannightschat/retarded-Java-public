package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.service.DayStatisticsService;
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
	    List<Note> list = noteDao.findByOpenIdOrderByCreateDtDesc(openId);
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

    @Override
    public void unfreeze(String noteId) {
        // TODO 解冻账本，补充日、月统计数据，更新账本数据

    }

}
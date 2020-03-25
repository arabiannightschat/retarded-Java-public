package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.JsonUtils;
import org.springframework.stereotype.Service;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.dao.NoteDao;
import com.nights.retarded.notes.service.NoteService;

@Service("noteService")
public class NoteServiceImpl implements NoteService{

	@Resource(name = "noteDao")
	private NoteDao noteDao;

	@Override
	public List<Note> getAll() {
		return this.noteDao.findAll();
	}

    @Override
    public Note createNote(String openId, BigDecimal monthBudget) {
        Note note = new Note();
        note.setMonthBudget(monthBudget);
        note.setDayBudget(monthBudget.divide(BigDecimal.valueOf(30)));
        note.setDynamicDayBudget(note.getDayBudget());
        note.setCreateDt(new Date());
        note.setStatus(1);
        int days = DateUtils.dayToNextMonth(new Date());
        note.setBalance(note.getDayBudget().multiply(BigDecimal.valueOf(days)));
        noteDao.save(note);
        return note;
    }

    @Override
    public Note getCurrNote(String openId) {
	    List<Note> list = noteDao.findByOpenIdOrderByCreateDtDesc(openId);
	    return JsonUtils.getIndexZero(list);
    }

}
package com.nights.retarded.notes.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.nights.retarded.notes.model.entity.MonthStatistics;
import com.nights.retarded.notes.model.entity.Note;

public interface NoteService {

	List<Note> getAll();

    Note createNote(String openId, BigDecimal monthBudget);

    Note getCurrNote(String openId);

    List<Note> findByStatus(int i);

    void save(Note note);

    Note findById(String noteId);

    void unfreeze(Note noteId);

    Note getCurrNoteContainFreeze(String openId);

    MonthStatistics statMonthStatistics(Note note, Date monthFirst, Date monthLast);
}
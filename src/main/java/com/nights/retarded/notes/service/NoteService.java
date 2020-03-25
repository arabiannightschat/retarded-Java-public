package com.nights.retarded.notes.service;

import java.math.BigDecimal;
import java.util.List;

import com.nights.retarded.notes.model.Note;

public interface NoteService {

	List<Note> getAll();

    Note createNote(String openId, BigDecimal monthBudget);

    Note getCurrNote(String openId);
}
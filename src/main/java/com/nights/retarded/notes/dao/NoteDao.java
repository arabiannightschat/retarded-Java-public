package com.nights.retarded.notes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.notes.model.Note;

public interface NoteDao extends JpaRepository<Note, String>{

}
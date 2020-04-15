package com.nights.retarded.notes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.notes.model.Note;

import java.util.List;

public interface NoteDao extends JpaRepository<Note, String>{

    List<Note> findByOpenIdOrderByCreateDtDesc(String openId);

    List<Note> findByStatus(int i);
}
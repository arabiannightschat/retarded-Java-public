package com.nights.retarded.notes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.notes.model.Note;

import java.util.List;

public interface NoteDao extends JpaRepository<Note, String>{

    List<Note> findByStatus(int i);

    List<Note> findByOpenIdAndStatusOrderByCreateDtDesc(String openId, int i);

    List<Note> findByOpenIdOrderByCreateDtDesc(String openId);
}
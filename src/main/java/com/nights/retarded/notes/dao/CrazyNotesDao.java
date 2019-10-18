package com.nights.retarded.notes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.notes.model.CrazyNotes;


public interface CrazyNotesDao extends JpaRepository<CrazyNotes, String>{

	public List<CrazyNotes> findAllByOpenIdAndState(String openId,int state);
}

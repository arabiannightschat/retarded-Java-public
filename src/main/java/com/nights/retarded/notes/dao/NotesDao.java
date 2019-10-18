package com.nights.retarded.notes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.notes.model.Notes;


public interface NotesDao extends JpaRepository<Notes, String>{

	public List<Notes> findAllByOpenIdAndState(String openId,int state);
}

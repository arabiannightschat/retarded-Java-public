package com.nights.retarded.records.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.records.model.Record;

import java.util.Date;
import java.util.List;

public interface RecordDao extends JpaRepository<Record, String>{

    List<Record> findByNoteIdAndDtBetweenOrderByCreateDtDesc(String noteId, Date now, Date startTime);
}
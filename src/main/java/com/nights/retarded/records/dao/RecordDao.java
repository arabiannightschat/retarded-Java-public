package com.nights.retarded.records.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.records.model.Record;

import java.util.Date;
import java.util.List;

public interface RecordDao extends JpaRepository<Record, String>{

    List<Record> findByNoteIdAndDtBetweenOrderByDtDesc(String noteId, Date startTime, Date now);

    int countByNoteIdAndDt(String noteId, Date yesterday);

    int countByNoteIdAndDtLessThanEqual(String currNoteId, Date startTime);

    List<Record> findByNoteIdAndTypeIdAndDt(String noteId, String id, Date recordMonthFirst);

}
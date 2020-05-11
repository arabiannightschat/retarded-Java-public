package com.nights.retarded.notes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.notes.model.DayStatistics;

import java.util.Date;
import java.util.List;

public interface DayStatisticsDao extends JpaRepository<DayStatistics, String>{

    List<DayStatistics> findByNoteIdAndDtBetweenOrderByDtAsc(String noteId, Date now, Date startTime);

    DayStatistics findByNoteIdAndDt(String noteId, Date dt);

    List<DayStatistics> findByNoteIdAndDtGreaterThanEqualOrderByDtAsc(String noteId, Date dt);

    List<DayStatistics> findByNoteIdOrderByDtDesc(String noteId);

    List<DayStatistics> findByNoteIdAndDtGreaterThanEqualAndDtLessThanEqual(String noteId, Date monthFirst, Date monthLast);
}
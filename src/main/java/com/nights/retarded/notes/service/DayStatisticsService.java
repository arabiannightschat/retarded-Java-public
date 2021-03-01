package com.nights.retarded.notes.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nights.retarded.notes.model.entity.DayStatistics;
import com.nights.retarded.notes.model.entity.Note;

public interface DayStatisticsService {

	List<DayStatistics> getAll();

    Map getRecentData(String openId);

    DayStatistics findByNoteIdAndDt(String noteId, Date dt);

    void save(DayStatistics dayStatistics);

    void saveAll(List<DayStatistics> dayStatisticsList);

    DayStatistics initDayStatistics(Note note);

    List<DayStatistics> findByNoteIdAndDtGreaterThanEqualOrderByDtAsc(String noteId, Date dt);

    DayStatistics findFirstByNoteIdOrderByDtDesc(String noteId);

    void deleteLastDaysData(String noteId, int freezeDaysWithoutOperation);

    List<DayStatistics> findByNoteIdAndDtGreaterThanEqualAndDtLessThanEqual(String noteId, Date monthFirst, Date monthLast);
}
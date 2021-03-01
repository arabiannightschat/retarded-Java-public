package com.nights.retarded.notes.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nights.retarded.notes.model.entity.MonthStatistics;

public interface MonthStatisticsService {

	List<MonthStatistics> getAll();

    void save(MonthStatistics monthStatistics);

    MonthStatistics getLastMonthStatistics(String noteId);

    void importLastMonthBalance(String noteId, Integer isImport);

    MonthStatistics findByNoteIdAndDt(String noteId, Date monthFirst);

    Map<String, Object> getMonthStatistics(Date monthDate, String currNoteId);
}
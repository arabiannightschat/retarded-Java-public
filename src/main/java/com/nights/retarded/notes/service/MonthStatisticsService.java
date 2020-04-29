package com.nights.retarded.notes.service;

import java.util.Date;
import java.util.List;

import com.nights.retarded.notes.model.MonthStatistics;
import com.nights.retarded.notes.model.Note;

public interface MonthStatisticsService {

	List<MonthStatistics> getAll();

    void save(MonthStatistics monthStatistics);

    MonthStatistics getLastMonthStatistics(String noteId);

    void importLastMonthBalance(String noteId, Integer isImport);

    MonthStatistics findByNoteIdAndDt(String noteId, Date monthFirst);
}
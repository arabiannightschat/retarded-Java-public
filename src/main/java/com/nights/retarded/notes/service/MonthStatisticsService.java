package com.nights.retarded.notes.service;

import java.util.List;

import com.nights.retarded.notes.model.MonthStatistics;

public interface MonthStatisticsService {

	List<MonthStatistics> getAll();

    void save(MonthStatistics monthStatistics);

    MonthStatistics getLastMonthStatistics(String noteId);

    void importLastMonthBalance(String noteId, Integer isImport);
}
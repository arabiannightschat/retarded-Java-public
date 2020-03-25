package com.nights.retarded.notes.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.nights.retarded.notes.model.DayStatistics;

public interface DayStatisticsService {

	public List<DayStatistics> getAll();

    Map getRecentData(String openId);
}
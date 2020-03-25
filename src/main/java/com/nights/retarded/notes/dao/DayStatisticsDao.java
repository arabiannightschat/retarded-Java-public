package com.nights.retarded.notes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.notes.model.DayStatistics;

public interface DayStatisticsDao extends JpaRepository<DayStatistics, String>{

}
package com.nights.retarded.notes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.notes.model.MonthStatistics;

import java.util.Date;

public interface MonthStatisticsDao extends JpaRepository<MonthStatistics, String>{

    MonthStatistics findByNoteIdAndDt(String noteId, Date lastMonthFirstDay);
}
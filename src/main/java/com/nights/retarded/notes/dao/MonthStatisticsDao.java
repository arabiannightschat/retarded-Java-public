package com.nights.retarded.notes.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.notes.model.entity.MonthStatistics;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MonthStatisticsDao extends JpaRepository<MonthStatistics, String>{

    MonthStatistics findByNoteIdAndDt(String noteId, Date lastMonthFirstDay);

    List<MonthStatistics> findByNoteIdOrderByDtDesc(String noteId);

    @Query(value = "SELECT sum(r.money) as data, t.name as name FROM records_record r LEFT JOIN records_type t ON t.type_id = r.type_id " +
            "WHERE r.note_id = :noteId AND t.type = 0 AND dt >= :startTime AND dt <= :endTime GROUP BY r.type_id" ,nativeQuery = true)
    List<Map<String, Object>> statSumByType(@Param("noteId") String noteId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
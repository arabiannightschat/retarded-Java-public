package com.nights.retarded.statistics.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nights.retarded.statistics.model.CrazyDaily;

public interface CrazyDailyDao extends JpaRepository<CrazyDaily, String>{

	CrazyDaily getByCrazyIdAndDt(String notesId, Date dt);

	@Query(value = "select * from statistics_crazy_daily\n" + 
			"where crazy_id = :notesId and dt >= :startDt and dt <= :endDt\n" +
			"order by dt" , nativeQuery = true)
	List<CrazyDaily> getFromDtToDt(String notesId, Date startDt, Date endDt);
	
	@Query(value = "select * from statistics_crazy_daily where crazy_id = :notesId "
			+ "and dt > :startDt order by dt" , nativeQuery = true)
	List<CrazyDaily> getFromDt(String notesId, Date startDt);

	List<CrazyDaily> getByDt(Date dt);

	List<CrazyDaily> getByCrazyId(String crazyNotesId);
}
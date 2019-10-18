package com.nights.retarded.records.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nights.retarded.records.model.CrazyRecords;

public interface CrazyRecordsDao extends JpaRepository<CrazyRecords, String>{

	@Query(value = "select rcr.records_id as recordsId,rcr.money,rt.name,rt.icon,rt.type from records_crazy_records rcr\n" + 
			"left join records_type rt on rt.type_id = rcr.type_id\n" + 
			"where notes_id = :notesId and dt = :dt\n" +
			"order by rcr.create_dt" ,nativeQuery = true)
	List<Map<String,Object>> findCrazyRecords(String notesId, Date dt);

	@Query(value = "select rcr.money,rt.name,rt.icon,rcr.dt from records_crazy_records rcr\n" + 
			"left join records_type rt on rt.type_id = rcr.type_id\n" + 
			"where notes_id = :notesId and dt >= :startDt and dt <= :endDt\n" + 
			"order by rcr.create_dt" ,nativeQuery = true)
	List<Map<String, Object>> getFromDtToDt(String notesId, Date startDt, Date endDt);

	List<CrazyRecords> getByNotesId(String crazyNotesId);

}
package com.nights.retarded.records.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nights.retarded.records.model.RecordsType;

public interface RecordsTypeDao extends JpaRepository<RecordsType, String>{

	List<RecordsType> findAllByIsAlternative(int Alternative);

}
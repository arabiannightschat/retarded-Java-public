package com.nights.retarded.records.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.records.model.RecordsType;

import java.util.List;

public interface RecordsTypeDao extends JpaRepository<RecordsType, String>{

    List<RecordsType> findByCommonUseOrderByDefaultIxAsc(int i);
}
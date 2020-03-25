package com.nights.retarded.records.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.records.model.Record;

public interface RecordDao extends JpaRepository<Record, String>{

}
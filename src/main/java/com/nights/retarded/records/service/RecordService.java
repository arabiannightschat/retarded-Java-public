package com.nights.retarded.records.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.nights.retarded.records.model.RecentRecords;
import com.nights.retarded.records.model.Record;

public interface RecordService {

	List<Record> getAll();

    List<RecentRecords> getRecentRecords(String openId);

    void addRecord(String recordTypeId, BigDecimal money, String description, Date dt, String openId);
}
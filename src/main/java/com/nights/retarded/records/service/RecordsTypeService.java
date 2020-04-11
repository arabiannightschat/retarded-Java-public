package com.nights.retarded.records.service;

import java.util.List;
import java.util.Map;

import com.nights.retarded.records.model.RecordsType;

public interface RecordsTypeService {

	List<RecordsType> getAll();

    Map<String, Object> getTypes(String openId);
}
package com.nights.retarded.records.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.nights.retarded.records.model.RecordsType;
import com.nights.retarded.records.dao.RecordsTypeDao;
import com.nights.retarded.records.service.RecordsTypeService;

@Service("recordsTypeService")
public class RecordsTypeServiceImpl implements RecordsTypeService{

	@Resource(name = "recordsTypeDao")
	private RecordsTypeDao recordsTypeDao;

	@Override
	public List<RecordsType> getAll() {
		return this.recordsTypeDao.findAll();
	}

}
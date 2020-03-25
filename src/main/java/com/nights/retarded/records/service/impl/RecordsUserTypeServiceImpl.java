package com.nights.retarded.records.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.nights.retarded.records.model.RecordsUserType;
import com.nights.retarded.records.dao.RecordsUserTypeDao;
import com.nights.retarded.records.service.RecordsUserTypeService;

@Service("recordsUserTypeService")
public class RecordsUserTypeServiceImpl implements RecordsUserTypeService{

	@Resource(name = "recordsUserTypeDao")
	private RecordsUserTypeDao recordsUserTypeDao;

	@Override
	public List<RecordsUserType> getAll() {
		return this.recordsUserTypeDao.findAll();
	}

}
package com.nights.retarded.statistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.nights.retarded.statistics.model.CrazyDaily;
import com.nights.retarded.statistics.dao.CrazyDailyDao;
import com.nights.retarded.statistics.service.CrazyDailyService;

@Service("crazyDailyService")
public class CrazyDailyServiceImpl implements CrazyDailyService{

	@Resource(name = "crazyDailyDao")
	private CrazyDailyDao crazyDailyDao;

	@Override
	public List<CrazyDaily> getAll() {
		return this.crazyDailyDao.findAll();
	}

}
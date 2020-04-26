package com.nights.retarded.notes.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.nights.retarded.notes.model.MonthStatistics;
import com.nights.retarded.notes.dao.MonthStatisticsDao;
import com.nights.retarded.notes.service.MonthStatisticsService;

@Service("monthStatisticsService")
public class MonthStatisticsServiceImpl implements MonthStatisticsService{

	@Resource(name = "monthStatisticsDao")
	private MonthStatisticsDao monthStatisticsDao;

	@Override
	public List<MonthStatistics> getAll() {
		return this.monthStatisticsDao.findAll();
	}

}
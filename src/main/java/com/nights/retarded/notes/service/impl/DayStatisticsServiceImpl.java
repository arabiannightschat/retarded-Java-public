package com.nights.retarded.notes.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.dao.DayStatisticsDao;
import com.nights.retarded.notes.service.DayStatisticsService;

@Service("dayStatisticsService")
public class DayStatisticsServiceImpl implements DayStatisticsService{

	@Resource(name = "dayStatisticsDao")
	private DayStatisticsDao dayStatisticsDao;

	@Override
	public List<DayStatistics> getAll() {
		return this.dayStatisticsDao.findAll();
	}

    @Override
    public Map getRecentData(String openId) {
        return null;
    }


}
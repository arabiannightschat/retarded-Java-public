package com.nights.retarded.notes.controller;

import com.nights.retarded.base.BaseController;
import com.nights.retarded.notes.service.DayStatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("api/notes/dayStatistics")
public class DayStatisticsController extends BaseController {

	@Resource(name="dayStatisticsService")
	private DayStatisticsService dayStatisticsService;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public Map getAll() {
		return Success(dayStatisticsService.getAll());
	}

	@GetMapping("getRecentData")
    public Map getRecentData() {
        return Success(dayStatisticsService.getRecentData(getOpenId()));
    }

}
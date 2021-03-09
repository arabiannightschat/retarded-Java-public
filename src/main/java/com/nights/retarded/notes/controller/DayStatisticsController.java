package com.nights.retarded.notes.controller;

import com.nights.retarded.base.baseController.BaseController;
import com.nights.retarded.base.baseController.Result;
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
	public Result getAll() {
		return Success(dayStatisticsService.getAll());
	}

	@GetMapping("getRecentData")
    public Result getRecentData() {
        return Success(dayStatisticsService.getRecentData(getOpenId()));
    }

}
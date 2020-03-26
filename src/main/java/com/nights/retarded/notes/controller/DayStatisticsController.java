package com.nights.retarded.notes.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.nights.retarded.base.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.service.DayStatisticsService;

import io.swagger.annotations.ApiOperation;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("api/notes/dayStatistics")
public class DayStatisticsController extends BaseController {

	@Resource(name="dayStatisticsService")
	private DayStatisticsService dayStatisticsService;

	@ApiOperation(value="查询所有")
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public String getAll() {
		return objectToJson(dayStatisticsService.getAll());
	}

	@GetMapping("getRecentData")
    public Map getRecentData() {
        return Success(dayStatisticsService.getRecentData(getOpenId()));
    }

}
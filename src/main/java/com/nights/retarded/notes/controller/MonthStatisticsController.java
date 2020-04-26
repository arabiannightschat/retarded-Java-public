package com.nights.retarded.notes.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.service.MonthStatisticsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/notes/monthStatistics")
public class MonthStatisticsController {

	@Resource(name="monthStatisticsService")
	private MonthStatisticsService monthStatisticsService;

	@ApiOperation(value="查询所有")
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public String getAll() {
		// HttpServletRequest request
		//String openId = JsonUtils.requestToOpenId(request);
		return JsonUtils.objectToJson(monthStatisticsService.getAll());
	}
}
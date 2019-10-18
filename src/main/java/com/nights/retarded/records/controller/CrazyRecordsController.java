package com.nights.retarded.records.controller;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.records.service.CrazyRecordsService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/records/crazyRecords")
public class CrazyRecordsController {

	@Resource(name="crazyRecordsService")
	private CrazyRecordsService crazyRecordsService;

	@ApiOperation(value="查询当日疯狂账目")
	@RequestMapping(value = "getCurrDateRecords", method = RequestMethod.GET)
	public String getCurrDateRecords(String notesId,String dt) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return JsonUtils.objectToJson(crazyRecordsService.getCurrDateRecords(notesId,sdf.parse(dt)));
	}
	
	@ApiOperation(value="增加疯狂账目")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(String crazyRecords) {
		if(this.crazyRecordsService.add(crazyRecords)) {
			return "ok";
		}
		return "error";
	}
	
	@ApiOperation(value="删除疯狂账目")
	@RequestMapping(value = "del", method = RequestMethod.GET)
	public String del(String recordsId) {
		this.crazyRecordsService.del(recordsId);
		return "ok";
	}
	
	@ApiOperation(value="查询疯狂账目")
	@RequestMapping(value = "getCrazyRecords", method = RequestMethod.GET)
	public String getCrazyRecords(String notesId,String startDt,String endDt) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return JsonUtils.objectToJson(crazyRecordsService.getCrazyRecords(notesId,sdf.parse(startDt),sdf.parse(endDt)));
	}
	
	
}
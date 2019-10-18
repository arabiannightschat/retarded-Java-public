package com.nights.retarded.records.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.records.service.RecordsTypeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/records/recordsType")
public class RecordsTypeController {

	@Resource(name="recordsTypeService")
	private RecordsTypeService recordsTypeService;

	@ApiOperation(value="查询用户的所有类型")
	@RequestMapping(value = "getUserRecordsType", method = RequestMethod.GET)
	public String getUserRecordsType(HttpServletRequest request) {
		String openId = JsonUtils.requestToOpenId(request);
		return JsonUtils.mapToJson(recordsTypeService.getUserRecordsType(openId));
	}
	
	@ApiOperation(value="查询（用户未拥有的）系统默认类型")
	@RequestMapping(value = "getUnusedDefaultType", method = RequestMethod.GET)
	public String getUnusedDefaultType(HttpServletRequest request) {
		String openId = JsonUtils.requestToOpenId(request);
		return JsonUtils.mapToJson(recordsTypeService.getUnusedDefaultType(openId)) ;
	}
	
	@ApiOperation(value="查询用户的类型及（用户未拥有的）系统默认类型")
	@RequestMapping(value = "getUserAndUnusedDefaultType", method = RequestMethod.GET)
	public String getUserAndUnusedDefaultType(HttpServletRequest request) {
		String openId = JsonUtils.requestToOpenId(request);
		return JsonUtils.mapToJson(recordsTypeService.getUserAndUnusedDefaultType(openId)) ;
	}
	
	@ApiOperation(value="把初始类型给用户")
	@RequestMapping(value = "addAlternative2User", method = RequestMethod.GET)
	public void addAlternative2User(HttpServletRequest request) {
		String openId = JsonUtils.requestToOpenId(request);
		recordsTypeService.addAlternative2User(openId);
	}
	
	@ApiOperation(value="提交类型列表")
	@RequestMapping(value = "updateUserTypeList", method = RequestMethod.GET)
	public void updateUserTypeList(HttpServletRequest request,String listJson) {
		String openId = JsonUtils.requestToOpenId(request);
		recordsTypeService.updateUserTypeList(openId,listJson);
	}
}
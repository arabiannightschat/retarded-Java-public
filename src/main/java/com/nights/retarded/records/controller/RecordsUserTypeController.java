package com.nights.retarded.records.controller;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.records.service.RecordsUserTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/records/recordsUserType")
public class RecordsUserTypeController {

	@Resource(name="recordsUserTypeService")
	private RecordsUserTypeService recordsUserTypeService;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public String getAll() {
		// HttpServletRequest request
		//String openId = JsonUtils.requestToOpenId(request);
		return JsonUtils.objectToJson(recordsUserTypeService.getAll());
	}
}
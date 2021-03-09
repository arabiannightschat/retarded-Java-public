package com.nights.retarded.records.controller;

import com.nights.retarded.base.baseController.BaseController;
import com.nights.retarded.base.baseController.Result;
import com.nights.retarded.utils.JsonUtils;
import com.nights.retarded.records.service.RecordsUserTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/records/recordsUserType")
public class RecordsUserTypeController extends BaseController {

	@Resource(name="recordsUserTypeService")
	private RecordsUserTypeService recordsUserTypeService;

	@GetMapping(value = "getAll")
	public Result getAll() {
		return Success(recordsUserTypeService.getAll());
	}
}
package com.nights.retarded.records.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.nights.retarded.base.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.records.service.RecordsTypeService;

import io.swagger.annotations.ApiOperation;

import java.util.Map;

@RestController
@RequestMapping("api/records/recordsType")
public class RecordsTypeController extends BaseController {

	@Resource(name="recordsTypeService")
	private RecordsTypeService recordsTypeService;

	@ApiOperation(value="查询所有")
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public Map getAll() {
		return Success(recordsTypeService.getAll());
	}

	@GetMapping("getTypes")
    public Map getTypes(){
        return Success(recordsTypeService.getTypes(getOpenId()));
    }
}
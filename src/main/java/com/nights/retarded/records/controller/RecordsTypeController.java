package com.nights.retarded.records.controller;

import com.nights.retarded.base.BaseController;
import com.nights.retarded.records.service.RecordsTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("api/records/recordsType")
public class RecordsTypeController extends BaseController {

	@Resource(name="recordsTypeService")
	private RecordsTypeService recordsTypeService;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public Map getAll() {
		return Success(recordsTypeService.getAll());
	}

	@GetMapping("getTypes")
    public Map getTypes(){
        return Success(recordsTypeService.getTypes(getOpenId()));
    }
}
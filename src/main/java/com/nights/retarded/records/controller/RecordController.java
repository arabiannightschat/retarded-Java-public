package com.nights.retarded.records.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.nights.retarded.base.BaseController;
import com.nights.retarded.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.records.service.RecordService;

import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("api/records/record")
public class RecordController extends BaseController {

	@Resource(name="recordService")
	private RecordService recordService;

	@ApiOperation(value="查询所有")
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public String getAll() {
		return JsonUtils.objectToJson(recordService.getAll());
	}

	@GetMapping("getRecentRecords")
    public Map getRecentRecords(){
        return Success(recordService.getRecentRecords(getOpenId()));
    }

    @PostMapping("addRecord")
    public Map addRecord(String recordTypeId, BigDecimal money, String description, @RequestParam(required = false) Date dt){
	    if(dt == null) {
	        dt = new Date();
        }
        recordService.addRecord(recordTypeId, money, description, dt, getCurrNoteId());
        return Success();
    }

    @PostMapping("delRecord")
    public Map delRecord(String recordId) {
	    recordService.delRecord(recordId);
	    return Success();
    }
}
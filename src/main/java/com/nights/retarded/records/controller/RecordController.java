package com.nights.retarded.records.controller;

import com.nights.retarded.base.baseController.BaseController;
import com.nights.retarded.base.baseController.Result;
import com.nights.retarded.utils.JsonUtils;
import com.nights.retarded.records.service.RecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("api/records/record")
public class RecordController extends BaseController {

	@Resource(name="recordService")
	private RecordService recordService;

	@GetMapping(value = "getAll")
	public Result getAll() {
		return Success(recordService.getAll());
	}

    @PostMapping("addRecord")
    public Result addRecord(String recordTypeId, BigDecimal money, String description, @RequestParam(required = false) Date dt){
	    if(dt == null) {
	        dt = new Date();
        }
        recordService.addRecord(recordTypeId, money, description, dt, getCurrNoteId());
        return Success();
    }

    @PostMapping("delRecord")
    public Result delRecord(String recordId) {
	    recordService.delRecord(recordId);
	    return Success();
    }

    @GetMapping("recordsLoading")
    public Result recordsLoading(int recordsLoadingCount) {
	    return Success(recordService.recordsLoading(recordsLoadingCount, getCurrNoteId()));
    }
}
package com.nights.retarded.notes.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.nights.retarded.base.BaseController;
import org.springframework.web.bind.annotation.*;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.service.MonthStatisticsService;

import io.swagger.annotations.ApiOperation;

import java.util.Map;

@RestController
@RequestMapping("api/notes/monthStatistics")
public class MonthStatisticsController extends BaseController {

	@Resource(name="monthStatisticsService")
	private MonthStatisticsService monthStatisticsService;

	@ApiOperation(value="查询所有")
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public Map getAll() {
		return Success(monthStatisticsService.getAll());
	}

	@GetMapping("getLastMonthStatistics")
    public Map getLastMonthStatistics(){
        return Success(monthStatisticsService.getLastMonthStatistics(getCurrNoteId()));
    }

    // 将上月超支/省下的xx元在本月预算中扣除/添加到本月预算中
    @PostMapping("importLastMonthBalance")
    public Map importLastMonthBalance(Integer isImport){
	    monthStatisticsService.importLastMonthBalance(getCurrNoteId(), isImport);
	    return Success();
    }
}
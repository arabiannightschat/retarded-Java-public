package com.nights.retarded.notes.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.nights.retarded.base.BaseController;
import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.notes.model.MonthStatistics;
import org.springframework.web.bind.annotation.*;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.service.MonthStatisticsService;

import io.swagger.annotations.ApiOperation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
	    Map<String, Object> map = new HashMap<>();
        MonthStatistics monthStatistics = monthStatisticsService.getLastMonthStatistics(getCurrNoteId());
	    map.put("lastMonthStatistics", monthStatistics);
        Date lastDateOfLastMonth = DateUtils.monthLastDay(monthStatistics.getDt());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
	    map.put("lastDateOfLastMonth", sdf.format(lastDateOfLastMonth));
        return Success(map);
    }

    // 将上月超支/省下的xx元在本月预算中扣除/添加到本月预算中
    @PostMapping("importLastMonthBalance")
    public Map importLastMonthBalance(Integer isImport){
	    monthStatisticsService.importLastMonthBalance(getCurrNoteId(), isImport);
	    return Success();
    }
}
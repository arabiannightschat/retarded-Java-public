package com.nights.retarded.notes.controller;

import com.nights.retarded.base.baseController.BaseController;
import com.nights.retarded.base.baseController.Result;
import com.nights.retarded.utils.DateUtils;
import com.nights.retarded.notes.model.entity.MonthStatistics;
import com.nights.retarded.notes.service.MonthStatisticsService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/notes/monthStatistics")
public class MonthStatisticsController extends BaseController {

	@Resource(name="monthStatisticsService")
	private MonthStatisticsService monthStatisticsService;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public Result getAll() {
		return Success(monthStatisticsService.getAll());
	}

    /**
     * 用于月结算展示
     * @return
     */
	@GetMapping("getLastMonthStatistics")
    public Result getLastMonthStatistics(){
	    Map<String, Object> map = new HashMap<>();
        MonthStatistics monthStatistics = monthStatisticsService.getLastMonthStatistics(getCurrNoteId());
	    map.put("lastMonthStatistics", monthStatistics);
        Date lastDateOfLastMonth = DateUtils.monthEnd(monthStatistics.getDt());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
	    map.put("lastDateOfLastMonth", sdf.format(lastDateOfLastMonth));
        return Success(map);
    }

    // 将上月超支/省下的xx元在本月预算中扣除/添加到本月预算中
    @PostMapping("importLastMonthBalance")
    public Result importLastMonthBalance(Integer isImport){
	    monthStatisticsService.importLastMonthBalance(getCurrNoteId(), isImport);
	    return Success();
    }

    /**
     * 用于统计页
     */
    @GetMapping("getMonthStatistics")
    public Result getMonthStatistics(Date monthDate){
        return Success(monthStatisticsService.getMonthStatistics(monthDate, getCurrNoteId()));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
}
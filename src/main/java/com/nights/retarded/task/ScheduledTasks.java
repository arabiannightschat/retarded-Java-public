package com.nights.retarded.task;

import com.nights.retarded.utils.DateUtils;
import com.nights.retarded.notes.model.entity.DayStatistics;
import com.nights.retarded.notes.model.entity.MonthStatistics;
import com.nights.retarded.notes.model.entity.Note;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.MonthStatisticsService;
import com.nights.retarded.notes.service.NoteService;
import com.nights.retarded.records.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {
	
	@Autowired
    private NoteService noteService;

	@Autowired
    private DayStatisticsService dayStatisticsService;

	@Autowired
    private MonthStatisticsService monthStatisticsService;

	@Autowired
    private RecordService recordService;
	
    @Scheduled(cron="0 0 0 * * *")
    public void updateDayStatistics() {

        List<Note> notes = noteService.findByStatus(1);
        for(Note note : notes){

            DayStatistics dayStatistics = dayStatisticsService.initDayStatistics(note);
            BigDecimal dynamicDayBudget = recordService.getDynamicDayBudgetTask(new Date(), note.getBalance());
            note.setDynamicDayBudget(dynamicDayBudget);
            dayStatisticsService.save(dayStatistics);

            // 记录连续没记账天数
            Date yesterday = DateUtils.addDay(DateUtils.toDaySdf(new Date()), -1);
            if(recordService.countByNoteIdAndDt(note.getNoteId(), yesterday) > 0){
                note.setDaysWithoutOperation(0);
            } else {
                note.setDaysWithoutOperation(note.getDaysWithoutOperation() + 1);
            }
            // 连续不记账7天则冻结账本并删掉这些天的统计数据
            int freezeDaysWithoutOperation = 5;
            if(note.getDaysWithoutOperation() >= freezeDaysWithoutOperation){
                note.setStatus(0);
                dayStatisticsService.deleteLastDaysData(note.getNoteId(), freezeDaysWithoutOperation);
            }
            noteService.save(note);
        }
    }

    @Scheduled(cron="0 15 0 1 * *")
    public void updateMonthStatistics() {

        // 每月1号0:15执行
        // 清零数据，按下月重新计算账本当前信息，并标记账本为未结算，提示用户上月结算情况，并提供功能：'将上月超支/省下的xx元在本月预算中扣除/添加到本月预算中'
        // 1. 创建上月统计对象并入库
        // 2. 重新计算当前账本数据信息
        List<Note> notes = noteService.findByStatus(1);
        for(Note note : notes){

            // 写入上个月的月统计数据
            MonthStatistics monthStatistics = new MonthStatistics();
            monthStatistics.setDt(DateUtils.lastMonthFirstDay());
            monthStatistics.setBalance(note.getBalance());

            // 获取平均日花销
            List<DayStatistics> monthDaysRealList = dayStatisticsService.
                    findByNoteIdAndDtGreaterThanEqualAndDtLessThanEqual(note.getNoteId(), monthStatistics.getDt(), DateUtils.monthEnd(monthStatistics.getDt()));
            int monthDaysReal = monthDaysRealList.size();
            monthStatistics.setAvgDaySpending(note.getBalance().divide(BigDecimal.valueOf(monthDaysReal),2, BigDecimal.ROUND_HALF_UP));
            monthStatistics.setMonthBudget(note.getMonthBudget());
            monthStatistics.setMonthSpending(monthStatistics.getMonthBudget().subtract(monthStatistics.getBalance()));
            monthStatistics.setNoteId(note.getNoteId());

            // 默认在月结时，重置账本余额，这里是一个标识
            monthStatistics.setIsClear(1);
            monthStatisticsService.save(monthStatistics);

            // 账本状态设置为：还未设定是否继承余额
            note.setMonthStatisticsState(0);
            int dayToNextMonth = DateUtils.dayToNextMonth(DateUtils.currMonthFirstDay());
            note.setMonthBudget(note.getDayBudget().multiply(BigDecimal.valueOf(dayToNextMonth)));
            note.setBalance(note.getMonthBudget());
            note.setDynamicDayBudget(note.getDayBudget());
            noteService.save(note);

            DayStatistics dayStatistics = dayStatisticsService.findByNoteIdAndDt(note.getNoteId(), DateUtils.toDaySdf(new Date()));
            if(dayStatistics != null) {
                dayStatistics.setBalance(note.getBalance());
                dayStatistics.setDynamicDayBudget(note.getDynamicDayBudget());
                dayStatisticsService.save(dayStatistics);
            }

        }

    }
}
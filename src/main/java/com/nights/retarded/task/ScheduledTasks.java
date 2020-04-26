package com.nights.retarded.task;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.DayStatisticsService;
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
    private RecordService recordService;
	
    @Scheduled(cron="0 0 0 * * *")
    public void updateDayStatistics() {

        List<Note> notes = noteService.findByStatus(1);
        for(Note note : notes){

            DayStatistics dayStatistics = dayStatisticsService.initDayStatistics(note);
            BigDecimal dynamicDayBudget = recordService.getDynamicDayBudget(new Date(), note.getBalance());
            note.setDynamicDayBudget(dynamicDayBudget);
            dayStatisticsService.save(dayStatistics);
        }
    }

    @Scheduled(cron="0 15 0 1 * *")
    public void updateMonthStatistics() {

        // 每月1号0:15执行
        // 清零数据，按下月重新计算账本当前信息，当用户本月第一次登录时，提示用户上月结算情况，并提供功能：'将上月超支/省下的xx元在本月预算中扣除/添加到本月预算中'
        // 1. 创建上月统计对象并入库
        // 2. 重新计算当前账本数据信息
        




    }
}
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
}
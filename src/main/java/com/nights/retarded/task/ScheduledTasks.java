package com.nights.retarded.task;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.NoteService;
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
	
    @Scheduled(cron="0 0 0 * * *")
    public void updateDayStatistics() {
        Date today = DateUtils.toDaySdf(new Date());
        List<Note> notes = noteService.findByStatus(1);
        for(Note note : notes){
            DayStatistics dayStatistics = new DayStatistics();
            dayStatistics.setNoteId(note.getNoteId());
            dayStatistics.setDaySpending(BigDecimal.ZERO);
            dayStatistics.setDayBudget(note.getDayBudget());
            dayStatistics.setDynamicDayBudget(note.getDynamicDayBudget());
            dayStatistics.setDt(today);
            dayStatisticsService.save(dayStatistics);
        }
    }
}
package com.nights.retarded.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nights.retarded.notes.dao.CrazyNotesDao;
import com.nights.retarded.notes.model.CrazyNotes;
import com.nights.retarded.statistics.dao.CrazyDailyDao;
import com.nights.retarded.statistics.model.CrazyDaily;

@Component
public class ScheduledTasks {
	
	@Resource(name = "crazyDailyDao")
	private CrazyDailyDao crazyDailyDao;
	
	@Resource(name = "crazyNotesDao")
	private CrazyNotesDao crazyNotesDao;
	
    @Scheduled(cron="0 0 0 * * *")
    public void reportCurrentTime() throws Exception {

    	Date today = new Date();
    	Date yesterday = new Date(today.getTime() - 24*60*60*1000);
    	List<CrazyDaily> dailys = crazyDailyDao.getByDt(yesterday);
    	
    	for (CrazyDaily yesterdayDaily : dailys) {
    		CrazyNotes crazyNotes = this.crazyNotesDao.getOne(yesterdayDaily.getCrazyId());
			double dailyLimit = crazyNotes.getDailyLimit();
    		// 增加新一天的统计数据
        	CrazyDaily crazyDaily = new CrazyDaily();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		crazyDaily.setDt(sdf.parse(sdf.format(today)));
    		crazyDaily.setCrazyId(yesterdayDaily.getCrazyId());
    		double dayStart = yesterdayDaily.getDayEnd() + dailyLimit;
    		double dayEnd = dayStart;
    		double daySettle = 0;
    		crazyDaily.setDayStart(dayStart);
    		crazyDaily.setDayEnd(dayEnd);
    		crazyDaily.setDaySettle(daySettle);
    		crazyDailyDao.save(crazyDaily);
		}
    	
    }
    

}
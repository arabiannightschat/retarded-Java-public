package com.nights.retarded.notes.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.model.DayStatistics;
import com.nights.retarded.notes.model.Note;
import com.nights.retarded.notes.service.DayStatisticsService;
import com.nights.retarded.notes.service.NoteService;
import com.nights.retarded.records.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nights.retarded.notes.model.MonthStatistics;
import com.nights.retarded.notes.dao.MonthStatisticsDao;
import com.nights.retarded.notes.service.MonthStatisticsService;

@Service("monthStatisticsService")
public class MonthStatisticsServiceImpl implements MonthStatisticsService{

	@Resource(name = "monthStatisticsDao")
	private MonthStatisticsDao monthStatisticsDao;

	@Autowired
    private NoteService noteService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private DayStatisticsService dayStatisticsService;

	@Override
	public List<MonthStatistics> getAll() {
		return this.monthStatisticsDao.findAll();
	}

    @Override
    public void save(MonthStatistics monthStatistics) {
        monthStatisticsDao.save(monthStatistics);
    }

    @Override
    public MonthStatistics getLastMonthStatistics(String noteId) {
        return JsonUtils.getIndexZero(monthStatisticsDao.findByNoteIdOrderByDtDesc(noteId));
    }

    @Override
    public void importLastMonthBalance(String noteId, Integer isImport) {
        Note note = noteService.findById(noteId);
	    if(isImport == 1){
	        // 如果需要引入上月结余或欠款，修改账本数据
            MonthStatistics monthStatistics = getLastMonthStatistics(noteId);
            monthStatistics.setIsClear(0);
            monthStatisticsDao.save(monthStatistics);
            int month = DateUtils.getMonth(DateUtils.monthFirstDay(monthStatistics.getDt()));
            recordService.addRecord("8a44deb8d83111e89d4100163e02uuuu", monthStatistics.getBalance(),
                    month + "月余额结转", DateUtils.monthFirstDay(new Date()), noteId);

        }
        // 标记为已处理
        note.setMonthStatisticsState(1);
        noteService.save(note);

    }

    @Override
    public MonthStatistics findByNoteIdAndDt(String noteId, Date monthFirst) {
        return monthStatisticsDao.findByNoteIdAndDt(noteId, monthFirst);
    }

}
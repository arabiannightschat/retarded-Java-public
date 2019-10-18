package com.nights.retarded.notes.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.dao.CrazyNotesDao;
import com.nights.retarded.notes.model.CrazyNotes;
import com.nights.retarded.notes.service.CrazyNotesService;
import com.nights.retarded.records.dao.CrazyRecordsDao;
import com.nights.retarded.records.model.CrazyRecords;
import com.nights.retarded.statistics.dao.CrazyDailyDao;
import com.nights.retarded.statistics.model.CrazyDaily;

@Service("crazyNotesService")
public class CrazyNotesServiceImpl implements CrazyNotesService{

	@Resource(name = "crazyNotesDao")
	private CrazyNotesDao crazyNotesDao;
	
	@Resource(name = "crazyDailyDao")
	private CrazyDailyDao crazyDailyDao;
	
	@Resource(name = "crazyRecordsDao")
	private CrazyRecordsDao crazyRecordsDao;

	@Override
	public CrazyNotes addCrazyNotes(String openId, double dailyLimit, int settleDate) throws Exception {
		CrazyNotes notes = new CrazyNotes();
		notes.setOpenId(openId);
		notes.setState(1);
		notes.setDailyLimit(dailyLimit);
		if(settleDate != 0) {
			notes.setSettleDate(settleDate);
		}
		notes.setName("疯狂账本");
		notes.setCreateDt(new Date());
		crazyNotesDao.save(notes);
		// 初始化统计数据
		CrazyDaily crazyDaily = new CrazyDaily();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		crazyDaily.setDt(sdf.parse(sdf.format(notes.getCreateDt())));
		crazyDaily.setCrazyId(notes.getCrazyId());
		double dayStart = dailyLimit;
		double dayEnd = dailyLimit;
		double daySettle = 0;
		crazyDaily.setDayStart(dayStart);
		crazyDaily.setDayEnd(dayEnd);
		crazyDaily.setDaySettle(daySettle);
		crazyDailyDao.save(crazyDaily);
		return notes;
	}

	@Override
	public CrazyNotes getCrazyNoteInfo(String notesId) {
		return crazyNotesDao.getOne(notesId);
	}

	@Override
	public void editCrazyNote(String crazyNotesJson) {
		CrazyNotes crazyNotes = JsonUtils.jsonToClass(crazyNotesJson, CrazyNotes.class);
		this.crazyNotesDao.save(crazyNotes);
		
	}

	@Override
	public void del(String crazyNotesId) {
		
		List<CrazyRecords> crazyRecordsList = this.crazyRecordsDao.getByNotesId(crazyNotesId);
		List<CrazyDaily> crazyDailyList = this.crazyDailyDao.getByCrazyId(crazyNotesId);
		for (CrazyDaily crazyDaily : crazyDailyList) {
			this.crazyDailyDao.delete(crazyDaily);
		}
		for (CrazyRecords cazyRecords : crazyRecordsList) {
			this.crazyRecordsDao.delete(cazyRecords);
		}
		this.crazyNotesDao.deleteById(crazyNotesId);
	}

}
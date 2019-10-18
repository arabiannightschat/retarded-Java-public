package com.nights.retarded.records.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nights.retarded.common.utils.DateUtils;
import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.notes.dao.CrazyNotesDao;
import com.nights.retarded.notes.model.CrazyNotes;
import com.nights.retarded.records.dao.CrazyRecordsDao;
import com.nights.retarded.records.dao.RecordsTypeDao;
import com.nights.retarded.records.model.CrazyRecords;
import com.nights.retarded.records.model.RecordsType;
import com.nights.retarded.records.service.CrazyRecordsService;
import com.nights.retarded.statistics.dao.CrazyDailyDao;
import com.nights.retarded.statistics.model.CrazyDaily;

@Service("crazyRecordsService")
public class CrazyRecordsServiceImpl implements CrazyRecordsService{

	@Resource(name = "crazyRecordsDao")
	private CrazyRecordsDao crazyRecordsDao;
	
	@Resource(name = "crazyNotesDao")
	private CrazyNotesDao crazyNotesDao;
	
	@Resource(name = "crazyDailyDao")
	private CrazyDailyDao crazyDailyDao;
	
	@Resource(name = "recordsTypeDao")
	private RecordsTypeDao recordsTypeDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Map<String,Object> getCurrDateRecords(String notesId, Date dt) {
		CrazyDaily crazyDaily = this.crazyDailyDao.getByCrazyIdAndDt(notesId,dt);
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			now = sdf.parse(sdf.format(now));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		CrazyDaily crazyToday = this.crazyDailyDao.getByCrazyIdAndDt(notesId,now);
		Map<String,Object> map = new HashMap<>();
		if(crazyDaily != null) {
			map.put("dayStart", crazyDaily.getDayStart());
			map.put("dayEnd", crazyDaily.getDayEnd());
			map.put("daySettle", crazyDaily.getDaySettle());
		}
		if(crazyToday != null) {
			map.put("monthSettle", crazyToday.getDayEnd());
		}
		map.put("dt", sdf.format(dt));
		map.put("recordsList", this.crazyRecordsDao.findCrazyRecords(notesId,dt));
		return map;
	}

	@Override
	public boolean add(String crazyRecordsJson) {
		CrazyRecords crazyRecords = JsonUtils.jsonToClass(crazyRecordsJson, CrazyRecords.class);
		crazyRecords.setCreateDt(new Date());
		this.crazyRecordsDao.save(crazyRecords);
		// 账目类型 0：支出，1：收入
		RecordsType recordsType = this.recordsTypeDao.getOne(crazyRecords.getTypeId());
		int type = recordsType.getType();
		// 金额
		double money = crazyRecords.getMoney()*(type == 0?-1:1);
		BigDecimal money_ = BigDecimal.valueOf(money);
		// 日结统计
		CrazyDaily crazyDaily = crazyDailyDao.getByCrazyIdAndDt(crazyRecords.getNotesId(), crazyRecords.getDt());
		if(crazyDaily == null) {
			return false;
		} else {
			crazyDaily.setDayEnd(BigDecimal.valueOf(crazyDaily.getDayEnd()).add(money_).doubleValue());
			crazyDaily.setDaySettle(BigDecimal.valueOf(crazyDaily.getDaySettle()).add(money_).doubleValue());
			this.crazyDailyDao.save(crazyDaily);
			// 之后的日期要重新计算
			List<CrazyDaily> historyDailys = crazyDailyDao.getFromDt(crazyRecords.getNotesId(), crazyRecords.getDt());
			for (CrazyDaily historyDaily : historyDailys) {
				historyDaily.setDayEnd(BigDecimal.valueOf(historyDaily.getDayEnd()).add(money_).doubleValue());
				historyDaily.setDayStart(BigDecimal.valueOf(historyDaily.getDayStart()).add(money_).doubleValue());
				this.crazyDailyDao.save(historyDaily);
			}
		}
		return true;
	}

	@Override
	public List<Map<String,Object>> getCrazyRecords(String notesId, Date startDt, Date endDt) throws Exception {
		
		// 查出这段时间的账目
		List<CrazyDaily> crazyDailyList = this.crazyDailyDao.getFromDtToDt(notesId,startDt,endDt);
		List<Map<String,Object>> list = this.crazyRecordsDao.getFromDtToDt(notesId,startDt,endDt);
		List<Map<String,Object>> records = new ArrayList<>();
		int index = 0;
		int indexDaily = 0;
		while(startDt.getTime() <= endDt.getTime()) {
			
			Map<String,Object> map = new HashMap<>();
			map.put("dt", sdf.format(startDt));
			if(indexDaily < crazyDailyList.size()) {
				CrazyDaily crazyDaily = crazyDailyList.get(indexDaily);
				if(startDt.getTime() == crazyDaily.getDt().getTime()) {
					map.put("dayStart", crazyDaily.getDayStart());
					map.put("dayEnd", crazyDaily.getDayEnd());
					map.put("daySettle", crazyDaily.getDaySettle());
					indexDaily ++;
				}
			}
			List<Map<String,Object>> recordsList = new ArrayList<>();
			for(;index < list.size(); index ++) {
				Map<String,Object> recordsMap = list.get(index);
				Object objDt = recordsMap.get("dt");
				if(objDt == null) {
					continue;
				}
				if(sdf.parse(objDt.toString()).getTime() == startDt.getTime()) {
					recordsList.add(recordsMap);
				} else {
					break;
				}
			}
			if(recordsList.size() > 0) {
				map.put("recordsList", recordsList);
			}
			if(map.size() > 1) {
				records.add(map);
			}
			startDt = DateUtils.addDay(startDt, 1);
		}
		return records;
	}

	@Override
	public void del(String recordsId) {
		CrazyRecords crazyRecords = this.crazyRecordsDao.getOne(recordsId);
		// 更改统计数据表
		RecordsType recordsType = this.recordsTypeDao.getOne(crazyRecords.getTypeId());
		int type = recordsType.getType();
		// 金额
		double money = crazyRecords.getMoney()*(type == 0?-1:1);
		BigDecimal money_ = BigDecimal.valueOf(money);
		CrazyDaily crazyDaily = crazyDailyDao.getByCrazyIdAndDt(crazyRecords.getNotesId(), crazyRecords.getDt());
		crazyDaily.setDayEnd(BigDecimal.valueOf(crazyDaily.getDayEnd()).subtract(money_).doubleValue());
		crazyDaily.setDaySettle(BigDecimal.valueOf(crazyDaily.getDaySettle()).subtract(money_).doubleValue());
		this.crazyDailyDao.save(crazyDaily);
		// 之后的日期要重新计算
		List<CrazyDaily> historyDailys = crazyDailyDao.getFromDt(crazyRecords.getNotesId(), crazyRecords.getDt());
		for (CrazyDaily historyDaily : historyDailys) {
			historyDaily.setDayEnd(BigDecimal.valueOf(historyDaily.getDayEnd()).subtract(money_).doubleValue());
			historyDaily.setDayStart(BigDecimal.valueOf(historyDaily.getDayStart()).subtract(money_).doubleValue());
			this.crazyDailyDao.save(historyDaily);
		}
		this.crazyRecordsDao.delete(crazyRecords);
	}

}

package com.nights.retarded.records.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CrazyRecordsService {

	// 查询当日账目
	public Map<String,Object> getCurrDateRecords(String openId, Date dt);

	// 增加疯狂账目
	public boolean add(String crazyRecordsJson);

	// 一段时间全部账目
	public List<Map<String,Object>> getCrazyRecords(String notesId, Date startDt, Date endDt) throws Exception;

	// 删除疯狂账目
	public void del(String recordsId);

}	
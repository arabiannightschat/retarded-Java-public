package com.nights.retarded.sys.service;

import com.nights.retarded.sys.model.LoginRecord;

import java.util.Date;
import java.util.List;

public interface LoginRecordService {
	
	/**
	 * 增加一条登录记录
	 * @param openId
	 */
	void addLoginRecord(String openId);

	List<LoginRecord> findByOpenIdAndDtAfter(String openId, Date dt);
}

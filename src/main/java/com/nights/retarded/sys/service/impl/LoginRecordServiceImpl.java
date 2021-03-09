package com.nights.retarded.sys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nights.retarded.sys.dao.LoginRecordDao;
import com.nights.retarded.sys.model.entity.LoginRecord;
import com.nights.retarded.sys.service.LoginRecordService;

@Service("loginRecordService")
public class LoginRecordServiceImpl implements LoginRecordService{
	
	@Resource(name = "loginRecordDao")
	private LoginRecordDao loginRecordDao;

	@Override
	public void addLoginRecord(String openId) {
		LoginRecord loginRecord = new LoginRecord();
		loginRecord.setDt(new Date());
		loginRecord.setOpenId(openId);
		loginRecordDao.save(loginRecord);
	}

	@Override
	public List<LoginRecord> findByOpenIdAndDtAfter(String openId, Date dt) {
		return loginRecordDao.findByOpenIdAndDtAfter(openId, dt);
	}

}

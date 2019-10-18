package com.nights.retarded.sys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nights.retarded.sys.dao.LoginRecordDao;
import com.nights.retarded.sys.dao.UserDao;
import com.nights.retarded.sys.model.LoginRecord;
import com.nights.retarded.sys.service.LoginRecordService;

@Service("loginRecordService")
public class LoginRecordServiceImpl implements LoginRecordService{
	
	@Resource(name = "loginRecordDao")
	private LoginRecordDao loginRecordDao;

	@Resource(name = "userDao")
	private UserDao userDao;

	@Override
	public void addLoginRecord(String openId) {
		LoginRecord loginRecord = new LoginRecord();
		loginRecord.setDt(new Date());
		loginRecord.setOpenId(openId);
		loginRecordDao.save(loginRecord);
	}


	@Override
	public boolean isFirstLogin(String openId) {
		if(loginRecordDao.getOpenIdCount(openId) > 0 && this.userDao.findByOpenId(openId) != null) { 
			return false;
		}else {
			return true;
		}
	}
}

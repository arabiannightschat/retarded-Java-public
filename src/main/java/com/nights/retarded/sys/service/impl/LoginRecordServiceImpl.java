package com.nights.retarded.sys.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import com.nights.retarded.sys.model.User;
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
	    User user = userDao.findByOpenId(openId);
        if(loginRecordDao.getOpenIdCount(openId) > 0 && user != null) {
		    user.setLoginCount(user.getLoginCount() + 1);
		    user.setLastLoginTime(new Date());
		    userDao.save(user);
			return false;
		}else {
			return true;
		}
	}
}

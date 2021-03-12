package com.nights.retarded.sys.service.impl;

import com.nights.retarded.utils.DateUtils;
import com.nights.retarded.utils.JsonUtils;
import com.nights.retarded.sys.dao.UserDao;
import com.nights.retarded.sys.model.entity.LoginRecord;
import com.nights.retarded.sys.model.entity.User;
import com.nights.retarded.sys.service.LoginRecordService;
import com.nights.retarded.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource(name = "userDao")
	private UserDao userDao;

	@Autowired
	private LoginRecordService loginRecordService;
	
	@Override
	public User findByOpenId(String openId) {
		User user = userDao.findByOpenId(openId); 
		return user;
	}

	@Override
	public void addUserInfo(String openId, String userInfo) {
		User user = JsonUtils.jsonToClass(userInfo, User.class);
		User dbUser = userDao.findByOpenId(openId);
		dbUser.setAvatarUrl(user.getAvatarUrl());
		dbUser.setCountry(user.getCountry());
        dbUser.setProvince(user.getProvince());
        dbUser.setCity(user.getCity());
        dbUser.setGender(user.getGender());
        dbUser.setLanguage(user.getLanguage());
		dbUser.setNickName(user.getNickName());
		userDao.save(dbUser);
	}

	@Override
	public void createUser(String openId) {
		User user = new User();
		user.setOpenId(openId);
		user.setCreateDt(new Date());
		user.setLastLoginTime(new Date());
		user.setLoginCount(1);
		user.setChargeDayCount(1);
		userDao.save(user);
	}

	@Override
	public void countLoginInfo(String openId) {
		Date now = new Date();
		User user = userDao.findByOpenId(openId);
		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginTime(now);
		// 如果是今日首次登陆，则记账天数统计 + 1
		List<LoginRecord> list = loginRecordService.findByOpenIdAndDtAfter(openId, DateUtils.toDaySdf(now));
		if(list == null || list.size() == 0) {
			user.setChargeDayCount(user.getChargeDayCount() + 1);
		}
		userDao.save(user);
	}


}

package com.nights.retarded.sys.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.sys.dao.UserDao;
import com.nights.retarded.sys.model.User;
import com.nights.retarded.sys.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource(name = "userDao")
	private UserDao userDao;
	
	@Override
	public User findByOpenId(String openId) {
		User user = userDao.findByOpenId(openId); 
		return user;
	}

	@Override
	public void addUserInfo(String openId, String userInfo) {
		User user = JsonUtils.jsonToClass(userInfo, User.class);
		User dbUser = userDao.findByOpenId(openId);
		user.setRem(dbUser.getRem());
		user.setCreateTime(dbUser.getCreateTime());
		user.setLastLoginTime(dbUser.getLastLoginTime());
		user.setLoginCount(dbUser.getLoginCount());
		user.setOpenId(openId);
		userDao.save(user);
	}

	@Override
	public void createUser(String openId) {
		User user = new User();
		user.setOpenId(openId);
		user.setCreateTime(new Date());
		user.setLastLoginTime(new Date());
		user.setLoginCount(1);
		userDao.save(user);
	}

	@Override
	public void countLoginInfo(String openId) {
		User user = userDao.findByOpenId(openId);
		user.setLoginCount(user.getLoginCount() + 1);
		user.setLastLoginTime(new Date());
		userDao.save(user);
	}


}

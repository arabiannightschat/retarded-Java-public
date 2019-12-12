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

	// 只保存UI信息，其他信息不变更
	@Override
	public void recordUI(String openId, String style) {
		Map<String,String> map = JsonUtils.jsonToStringMap(style);
		User user = userDao.findByOpenId(openId);
		if(user == null) {
			user = new User();
			user.setOpenId(openId);
			Date now = new Date();
			user.setCreateTime(now);
			user.setLastLoginTime(now);
			user.setLoginCount(1);
		}
		user.setColor(map.get("color"));
		user.setFrontColor(map.get("frontColor"));
		user.setSecondColor(map.get("secondColor"));
		user.setStyle(map.get("style"));
		userDao.save(user);
		
	}

	// 除UI信息、备注、创建时间、登录时间、登录次数，全部变更
	@Override
	public void addUserInfo(String openId, String userInfo) {
		User user = JsonUtils.jsonToClass(userInfo, User.class);
		User dbUser = userDao.findByOpenId(openId);
		user.setColor(dbUser.getColor());
		user.setStyle(dbUser.getStyle());
		user.setRem(dbUser.getRem());
		user.setFrontColor(dbUser.getFrontColor());
		user.setSecondColor(dbUser.getSecondColor());
		user.setCreateTime(dbUser.getCreateTime());
		user.setLastLoginTime(dbUser.getLastLoginTime());
		user.setLoginCount(dbUser.getLoginCount());
		user.setOpenId(openId);
		userDao.save(user);
		
	}

}

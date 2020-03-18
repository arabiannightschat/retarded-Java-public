package com.nights.retarded.sys.service;

import com.nights.retarded.sys.model.User;

public interface UserService {
	
	/**
	 * 获取用户信息
	 */
	User findByOpenId(String openId);


	/**
	 * 追加用户信息
	 */
	void addUserInfo(String openId, String userInfo);

	/**
	 * 新用户
	 */
	void createUser(String openId);

	/**
	 * 追加登录信息
	 */
	void appendLoginInfo(String openId);
}

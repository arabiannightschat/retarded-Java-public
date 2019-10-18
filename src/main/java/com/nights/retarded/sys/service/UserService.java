package com.nights.retarded.sys.service;

import com.nights.retarded.sys.model.User;

public interface UserService {
	
	/**
	 * 获取用户信息
	 * @param openId 
	 * @return
	 */
	public User findByOpenId(String openId);

	/**
	 * 一个新用户，默认外观
	 * @param openId
	 * @param style 
	 */
	public void recordUI(String openId, String style);
	
	/**
	 * 追加用户信息
	 */
	public void addUserInfo(String openId, String userInfo);

}

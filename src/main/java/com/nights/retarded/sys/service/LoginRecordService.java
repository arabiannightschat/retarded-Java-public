package com.nights.retarded.sys.service;

public interface LoginRecordService {
	
	/**
	 * 增加一条登录记录
	 * @param openId
	 */
	public void addLoginRecord(String openId);

	/**
	 * 查询是否第一次登录
	 * @param openId
	 * @return
	 */
	public boolean isFirstLogin(String openId);
}

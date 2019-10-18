package com.nights.retarded.records.service;

import java.util.Map;

public interface RecordsTypeService {

	/**
	 * 获取用户类型
	 * @param openId
	 * @return
	 */
	public Map<String,Object> getUserRecordsType(String openId);

	/**
	 * 将初始类型赋予用户
	 * @param openId
	 */
	public void addAlternative2User(String openId);

	/**
	 * 得到（未被使用的）默认类型
	 * @param openId
	 * @return
	 */
	public Map<String,Object> getUnusedDefaultType(String openId);
	
	/**
	 * 得到用户及系统默认类型
	 * @param openId
	 * @return
	 */
	public Map<String,Object> getUserAndUnusedDefaultType(String openId);

	/**
	 * 更新用户类型列表
	 * @param openId
	 * @param listJson
	 */
	public void updateUserTypeList(String openId, String listJson);

}
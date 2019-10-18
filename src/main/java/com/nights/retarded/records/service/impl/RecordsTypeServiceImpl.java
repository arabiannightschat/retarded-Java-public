package com.nights.retarded.records.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nights.retarded.common.utils.JsonUtils;
import com.nights.retarded.records.dao.RecordsTypeDao;
import com.nights.retarded.records.dao.UserTypeDao;
import com.nights.retarded.records.model.RecordsType;
import com.nights.retarded.records.model.UserType;
import com.nights.retarded.records.service.RecordsTypeService;

@Service("recordsTypeService")
public class RecordsTypeServiceImpl implements RecordsTypeService{

	@Resource(name = "recordsTypeDao")
	private RecordsTypeDao recordsTypeDao;
	
	@Resource(name = "userTypeDao")
	private UserTypeDao userTypeDao;

	@Override
	public Map<String,Object> getUserRecordsType(String openId) {
		List<Map<String,Object>> expendList = this.userTypeDao.getUserRecordsType(openId,0);
		List<Map<String,Object>> incomeList = this.userTypeDao.getUserRecordsType(openId,1);
		Map<String,Object> map = new HashMap<>();
		map.put("expendList", expendList);
		map.put("incomeList", incomeList);
		return map;
	}

	@Override
	public void addAlternative2User(String openId) {
		List<RecordsType> alternativeList = this.recordsTypeDao.findAllByIsAlternative(1);
		for (RecordsType recordsType : alternativeList) {
			UserType userType = new UserType();
			userType.setTypeId(recordsType.getTypeId());
			userType.setOpenId(openId);
			userType.setIx(recordsType.getDefaultIx());
			this.userTypeDao.save(userType);
		}
		
	}

	@Override
	public Map<String,Object> getUserAndUnusedDefaultType(String openId) {
		Map<String,Object> map = this.getUserRecordsType(openId);
		map.putAll(this.getUnusedDefaultType(openId));
		return map;
	}
	
	@Override
	public Map<String,Object> getUnusedDefaultType(String openId) {
		List<Map<String,Object>> defaultExpendList = this.userTypeDao.getUnusedDefaultType(openId,0);
		List<Map<String,Object>> defaultIncomeList = this.userTypeDao.getUnusedDefaultType(openId,1);
		Map<String,Object> map = new HashMap<>();
		map.put("defaultExpendList", defaultExpendList);
		map.put("defaultIncomeList", defaultIncomeList);
		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUserTypeList(String openId, String listJson) {
		// 删掉用户全部类型关系
		this.userTypeDao.deleteByOpenId(openId);
		// 增加用户类型关系
		List<Object> list = JsonUtils.jsonToList(listJson);
		for (Object object : list) {
			UserType userType = JsonUtils.objectToClass(object, UserType.class);
			userType.setOpenId(openId);
			this.userTypeDao.save(userType);
		}
		
	}

}
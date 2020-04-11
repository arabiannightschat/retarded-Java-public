package com.nights.retarded.records.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.nights.retarded.records.model.RecordsType;
import com.nights.retarded.records.dao.RecordsTypeDao;
import com.nights.retarded.records.service.RecordsTypeService;

@Service("recordsTypeService")
public class RecordsTypeServiceImpl implements RecordsTypeService{

	@Resource(name = "recordsTypeDao")
	private RecordsTypeDao recordsTypeDao;

	@Override
	public List<RecordsType> getAll() {
		return this.recordsTypeDao.findAll();
	}

    @Override
    public Map<String, Object> getTypes(String openId) {
	    List<RecordsType> commons = recordsTypeDao.findByCommonUseOrderByDefaultIxAsc(1);
	    List<RecordsType> others = recordsTypeDao.findByCommonUseOrderByDefaultIxAsc(0);
	    Map map = new HashMap();
	    map.put("commons", commons);
	    map.put("others", others);
        return map;
    }

}
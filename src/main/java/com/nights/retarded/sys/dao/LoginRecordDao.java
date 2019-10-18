package com.nights.retarded.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nights.retarded.sys.model.LoginRecord;
import org.springframework.data.repository.query.Param;

public interface LoginRecordDao extends JpaRepository<LoginRecord,String>{

	@Query("select count(lr) from LoginRecord lr where lr.openId = :openId")
	public int getOpenIdCount(@Param("openId") String openId);
	
}

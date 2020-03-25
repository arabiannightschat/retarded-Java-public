package com.nights.retarded.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nights.retarded.sys.model.LoginRecord;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LoginRecordDao extends JpaRepository<LoginRecord,String>{

    List<LoginRecord> findByOpenIdAndDtAfter(String openId, Date dt);
}

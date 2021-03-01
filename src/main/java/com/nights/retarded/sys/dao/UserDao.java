package com.nights.retarded.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.sys.model.User;

//如果不需要写实现类，直接继承 JpaRepository<User,String>
public interface UserDao extends JpaRepository<User,String>{

	User findByOpenId(String openId);
	
}

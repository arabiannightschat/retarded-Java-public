package com.nights.retarded.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nights.retarded.sys.model.entity.Feedback;

public interface FeedbackDao extends JpaRepository<Feedback, String>{

}
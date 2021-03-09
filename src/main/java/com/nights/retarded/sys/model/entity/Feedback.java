package com.nights.retarded.sys.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Feedback")
@Table(name = "sys_feedback")
@Data
public class Feedback {

	@JsonIgnore
	@Id
	private String openId;

	private String content;

	private String reply;

	private String wechatId;

}
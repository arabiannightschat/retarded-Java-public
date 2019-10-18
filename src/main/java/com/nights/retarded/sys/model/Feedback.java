package com.nights.retarded.sys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Feedback")
@Table(name = "sys_feedback")
public class Feedback implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="open_id")
	@JsonIgnore
	@Id
	private String openId;

	@Column(name="content")
	private String content;

	@Column(name="reply")
	private String reply;

	@Column(name="wechat_id")
	private String wechatId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

}
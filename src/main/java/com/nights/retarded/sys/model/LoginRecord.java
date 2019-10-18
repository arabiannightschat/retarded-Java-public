package com.nights.retarded.sys.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "LoginRecord")
@Table(name="sys_login_record")
public class LoginRecord implements Serializable{
	
	private static final long serialVersionUID = 1;

	/* 记录Id */
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String recordId;

	/* 微信公开Id */
    @Column(name="open_id")
    private String openId;
    
    /* 密码 */
    @Column(name="dt")
    private Date dt;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}
    
}

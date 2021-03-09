package com.nights.retarded.sys.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "User")
@Table(name = "sys_user")
@Data
public class User {

	@Id
	@JsonIgnore
	private String openId;

	private String nickName;

	private Integer chargeDayCount;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	private Integer loginCount;

	private String avatarUrl;

	private Integer gender;

	private String language;

	private String country;

	private String province;

	private String city;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDt;

}
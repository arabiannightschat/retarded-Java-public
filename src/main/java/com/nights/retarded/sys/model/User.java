package com.nights.retarded.sys.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "User")
@Table(name = "sys_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@JsonIgnore
	private String openId;

	@Column(name="nick_name")
	private String nickName;

	@Column(name="charge_day_count")
	private Integer chargeDayCount;

	@Column(name="last_login_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	@Column(name="login_count")
	private Integer loginCount;

	@Column(name="avatar_url")
	private String avatarUrl;

	@Column(name="gender")
	private Integer gender;

	@Column(name="language")
	private String language;

	@Column(name="country")
	private String country;

	@Column(name="province")
	private String province;

	@Column(name="city")
	private String city;

	@Column(name="create_dt")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDt;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getChargeDayCount() {
		return chargeDayCount;
	}

	public void setChargeDayCount(Integer chargeDayCount) {
		this.chargeDayCount = chargeDayCount;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

}
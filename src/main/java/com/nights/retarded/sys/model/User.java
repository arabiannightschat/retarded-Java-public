package com.nights.retarded.sys.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "User")
@Table(name="sys_user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1;
    
    /* openId */
    @Id
    @JsonIgnore
    private String openId;

	/* 微信昵称 */
    @Column(name="nick_name")
    private String nickName;
    
    /* 微信头像 */
    @Column(name="avatar_url")
    private String avatarUrl;
    
    /* 性别（1：男，2：女） */
    @Column(name="gender")
    private Integer gender;
    
    /* 语言 */
    @Column(name="language")
    private String language;
    
    /* 国家 */
    @Column(name="country")
    private String country;
    
    /* 省份 */
    @Column(name="province")
    private String province;
    
    /* 城市 */
    @Column(name="city")
    private String city;
    
    /* 颜色喜好 */
    @Column(name="color")
    private String color;
    
    /* 风格喜好(3,5,6) */
    @Column(name="style")
    private String style;
    
    /* 标题颜色 */
    @Column(name="front_color")
    private String frontColor;
    
    /* 标题颜色 */
    @Column(name="second_color")
    private String secondColor;
    
    /* 备注 */
    @Column(name="rem")
    private String rem;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getFrontColor() {
		return frontColor;
	}

	public void setFrontColor(String frontColor) {
		this.frontColor = frontColor;
	}

	public String getRem() {
		return rem;
	}

	public void setRem(String rem) {
		this.rem = rem;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSecondColor() {
		return secondColor;
	}

	public void setSecondColor(String secondColor) {
		this.secondColor = secondColor;
	}
    
    
}

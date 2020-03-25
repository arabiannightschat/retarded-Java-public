package com.nights.retarded.notes.model;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Note")
@Table(name = "notes_note")
public class Note implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String noteId;

	@Column(name="open_id")
	@JsonIgnore
	private String openId;

	@Column(name="name")
	private String name;

	@Column(name="balance")
	private BigDecimal balance;

	@Column(name="month_budget")
	private BigDecimal monthBudget;

	@Column(name="day_budget")
	private BigDecimal dayBudget;

	@Column(name="dynamic_day_budget")
	private BigDecimal dynamicDayBudget;

	@Column(name="status")
	private Integer status;

	@Column(name="create_dt")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createDt;

	@Column(name="close_dt")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date closeDt;

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getMonthBudget() {
		return monthBudget;
	}

	public void setMonthBudget(BigDecimal monthBudget) {
		this.monthBudget = monthBudget;
	}

	public BigDecimal getDayBudget() {
		return dayBudget;
	}

	public void setDayBudget(BigDecimal dayBudget) {
		this.dayBudget = dayBudget;
	}

	public BigDecimal getDynamicDayBudget() {
		return dynamicDayBudget;
	}

	public void setDynamicDayBudget(BigDecimal dynamicDayBudget) {
		this.dynamicDayBudget = dynamicDayBudget;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getCloseDt() {
		return closeDt;
	}

	public void setCloseDt(Date closeDt) {
		this.closeDt = closeDt;
	}

}
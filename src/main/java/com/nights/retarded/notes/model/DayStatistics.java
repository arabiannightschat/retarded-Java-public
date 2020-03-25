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

@Entity(name = "DayStatistics")
@Table(name = "notes_day_statistics")
public class DayStatistics implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String dayStatId;

	@Column(name="note_id")
	private String noteId;

	@Column(name="dt")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dt;

	@Column(name="day_spending")
	private BigDecimal daySpending;

	@Column(name="day_budget")
	private BigDecimal dayBudget;

	@Column(name="dynamic_day_budget")
	private BigDecimal dynamicDayBudget;

	public String getDayStatId() {
		return dayStatId;
	}

	public void setDayStatId(String dayStatId) {
		this.dayStatId = dayStatId;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public BigDecimal getDaySpending() {
		return daySpending;
	}

	public void setDaySpending(BigDecimal daySpending) {
		this.daySpending = daySpending;
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

}
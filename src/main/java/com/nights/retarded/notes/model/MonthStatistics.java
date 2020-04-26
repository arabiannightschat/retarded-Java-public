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

@Entity(name = "MonthStatistics")
@Table(name = "notes_month_statistics")
public class MonthStatistics implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String monthStatId;

	@Column(name="note_id")
	private String noteId;

	@Column(name="dt")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dt;

	@Column(name="month_spending")
	private BigDecimal monthSpending;

	@Column(name="avg_day_spending")
	private BigDecimal avgDaySpending;

	@Column(name="month_budget")
	private BigDecimal monthBudget;

	@Column(name="balance")
	private BigDecimal balance;

	public String getMonthStatId() {
		return monthStatId;
	}

	public void setMonthStatId(String monthStatId) {
		this.monthStatId = monthStatId;
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

	public BigDecimal getMonthSpending() {
		return monthSpending;
	}

	public void setMonthSpending(BigDecimal monthSpending) {
		this.monthSpending = monthSpending;
	}

	public BigDecimal getAvgDaySpending() {
		return avgDaySpending;
	}

	public void setAvgDaySpending(BigDecimal avgDaySpending) {
		this.avgDaySpending = avgDaySpending;
	}

	public BigDecimal getMonthBudget() {
		return monthBudget;
	}

	public void setMonthBudget(BigDecimal monthBudget) {
		this.monthBudget = monthBudget;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
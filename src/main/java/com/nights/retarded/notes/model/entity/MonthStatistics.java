package com.nights.retarded.notes.model.entity;

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

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "MonthStatistics")
@Table(name = "notes_month_statistics")
@Data
public class MonthStatistics implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String monthStatId;

	private String noteId;

	@Temporal(TemporalType.DATE)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date dt;

	private BigDecimal monthSpending;

	private BigDecimal avgDaySpending;

	private BigDecimal monthBudget;

	private BigDecimal balance;

	private Integer isClear;

}
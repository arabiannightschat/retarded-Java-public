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

@Entity(name = "DayStatistics")
@Table(name = "notes_day_statistics")
@Data
public class DayStatistics implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String dayStatId;

	private String noteId;

	@Temporal(TemporalType.DATE)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date dt;

	private BigDecimal daySpending;

	private BigDecimal dayBudget;

	private BigDecimal dynamicDayBudget;

    private BigDecimal balance;


}
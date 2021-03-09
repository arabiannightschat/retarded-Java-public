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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Note")
@Table(name = "notes_note")
@Data
public class Note implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String noteId;

	@JsonIgnore
	private String openId;

	private String name;

	private BigDecimal balance;

	private BigDecimal monthBudget;

	private BigDecimal dayBudget;

	private BigDecimal dynamicDayBudget;

	private Integer status;

	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createDt;

	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date closeDt;

	private Integer monthStatisticsState;

	private Integer daysWithoutOperation;


}
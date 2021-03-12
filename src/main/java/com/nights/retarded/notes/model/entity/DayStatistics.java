package com.nights.retarded.notes.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "DayStatistics")
@Table(name = "notes_day_statistics")
@Data
public class DayStatistics {

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
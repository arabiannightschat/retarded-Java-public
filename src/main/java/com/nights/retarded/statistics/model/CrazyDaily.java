package com.nights.retarded.statistics.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "CrazyDaily")
@Table(name = "statistics_crazy_daily")
public class CrazyDaily implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="statistics_crazy_id")
	private String statisticsCrazyId;

	@Column(name="crazy_id")
	private String crazyId;

	@Column(name="dt")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dt;

	@Column(name="day_start")
	private Double dayStart;

	@Column(name="day_end")
	private Double dayEnd;

    @Column(name="day_settle")
    private Double daySettle;

    public String getStatisticsCrazyId() {
        return statisticsCrazyId;
    }

    public void setStatisticsCrazyId(String statisticsCrazyId) {
        this.statisticsCrazyId = statisticsCrazyId;
    }

    public String getCrazyId() {
        return crazyId;
    }

    public void setCrazyId(String crazyId) {
        this.crazyId = crazyId;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Double getDayStart() {
        return dayStart;
    }

    public void setDayStart(Double dayStart) {
        this.dayStart = dayStart;
    }

    public Double getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(Double dayEnd) {
        this.dayEnd = dayEnd;
    }

    public Double getDaySettle() {
        return daySettle;
    }

    public void setDaySettle(Double daySettle) {
        this.daySettle = daySettle;
    }
}
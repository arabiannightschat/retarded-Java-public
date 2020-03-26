package com.nights.retarded.records.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RecentRecords {

    private Date dt;

    private String date;

    private BigDecimal totalSpending;

    private List<RecordVO> records;

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(BigDecimal totalSpending) {
        this.totalSpending = totalSpending;
    }

    public List<RecordVO> getRecords() {
        return records;
    }

    public void setRecords(List<RecordVO> records) {
        this.records = records;
    }
}

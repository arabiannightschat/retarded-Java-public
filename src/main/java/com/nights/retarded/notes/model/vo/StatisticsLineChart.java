package com.nights.retarded.notes.model.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class StatisticsLineChart {

    private List<String> categories = new ArrayList<>();
    private List<BigDecimal> daySpending = new ArrayList<>();
    private List<BigDecimal> dayBudget = new ArrayList<>();
}

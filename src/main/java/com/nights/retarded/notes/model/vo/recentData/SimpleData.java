package com.nights.retarded.notes.model.vo.recentData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleData {
    private BigDecimal balance;
    private Integer dayToNextMonth;
    private Integer year;
    private Integer month;
}

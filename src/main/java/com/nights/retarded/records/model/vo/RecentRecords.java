package com.nights.retarded.records.model.vo;

import com.nights.retarded.records.model.vo.RecordVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RecentRecords {

    private Date dt;

    private String date;

    private BigDecimal totalSpending;

    private List<RecordVO> records;

}

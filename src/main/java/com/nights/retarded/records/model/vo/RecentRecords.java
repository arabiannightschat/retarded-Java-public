package com.nights.retarded.records.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nights.retarded.records.model.vo.RecordVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class RecentRecords {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dt;

    private String date;

    private BigDecimal totalSpending;

    private List<RecordVO> records;

}

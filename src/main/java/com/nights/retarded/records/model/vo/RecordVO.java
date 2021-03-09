package com.nights.retarded.records.model.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecordVO {

    private String recordId;

    private BigDecimal money;

    private boolean isNew;

    private String icon;

    private String description;

}

package org.goodbye.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RevenueRecordDTO {
    private String id;
    private LocalDate recordDate;
    private String channel;
    private BigDecimal amount;
    private String remark;
    private String userId;
}
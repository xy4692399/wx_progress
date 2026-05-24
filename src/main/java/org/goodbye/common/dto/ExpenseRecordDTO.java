package org.goodbye.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseRecordDTO {
    private String id;
    private LocalDate recordDate;
    private String category;
    private BigDecimal amount;
    private String ticketUrl;
    private String remark;
    private String userId;
}
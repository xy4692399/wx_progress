package org.goodbye.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
public class DailySummaryDTO {
    private LocalDate summaryDate;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal ingredientCost;
    private BigDecimal netProfit;
    private Map<String, BigDecimal> channelBreakdown;
}
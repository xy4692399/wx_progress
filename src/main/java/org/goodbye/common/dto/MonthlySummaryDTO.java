package org.goodbye.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class MonthlySummaryDTO {
    private Integer year;
    private Integer month;
    private BigDecimal totalRevenue;
    private BigDecimal totalExpense;
    private BigDecimal ingredientCost;
    private BigDecimal fixedCost;
    private BigDecimal netProfit;
    private BigDecimal fixedCostRatio;
    private BigDecimal ingredientCostRatio;
    private List<DailySummaryDTO> dailySummaries;
    private Map<String, BigDecimal> channelBreakdown;
    private Map<String, BigDecimal> expenseCategoryBreakdown;
}
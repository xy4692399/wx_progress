package org.goodbye.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DishProfileDTO {
    private String id;
    private String dishName;
    private BigDecimal sellingPrice;
    private BigDecimal ingredientCost;
    private BigDecimal dailyAverageSales;
    private BigDecimal profit;
    private BigDecimal profitRate;
    private BigDecimal estimatedDailyProfit;
    private String userId;
}
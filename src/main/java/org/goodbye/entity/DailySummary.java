package org.goodbye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("daily_summary")
public class DailySummary {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String userId;

    private LocalDate summaryDate;

    private BigDecimal totalRevenue;

    private BigDecimal totalExpense;

    private BigDecimal ingredientCost;

    private BigDecimal fixedCost;

    private BigDecimal netProfit;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
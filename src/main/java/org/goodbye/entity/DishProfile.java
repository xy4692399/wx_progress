package org.goodbye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("dish_profile")
public class DishProfile {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String userId;

    private String dishName;

    private BigDecimal sellingPrice;

    private BigDecimal ingredientCost;

    private BigDecimal dailyAverageSales;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
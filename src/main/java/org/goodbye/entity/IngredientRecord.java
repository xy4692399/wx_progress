package org.goodbye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("ingredient_record")
public class IngredientRecord {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String userId;

    private String ingredientName;

    private LocalDate purchaseDate;

    private BigDecimal unitPrice;

    private BigDecimal quantity;

    private BigDecimal totalPrice;

    private String supplier;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
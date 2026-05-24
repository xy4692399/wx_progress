package org.goodbye.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IngredientRecordDTO {
    private String id;
    private String ingredientName;
    private LocalDate purchaseDate;
    private BigDecimal unitPrice;
    private BigDecimal quantity;
    private BigDecimal totalPrice;
    private String supplier;
    private String userId;
}
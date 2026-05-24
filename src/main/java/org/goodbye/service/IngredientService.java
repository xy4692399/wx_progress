package org.goodbye.service;

import org.goodbye.common.dto.IngredientRecordDTO;
import org.goodbye.entity.IngredientRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IngredientService {
    IngredientRecord addIngredient(String userId, IngredientRecordDTO dto);
    IngredientRecord updateIngredient(String id, String userId, IngredientRecordDTO dto);
    void deleteIngredient(String id, String userId);
    List<IngredientRecord> getIngredientsByDateRange(String userId, LocalDate startDate, LocalDate endDate);
    List<IngredientRecord> getAllIngredients(String userId);
    BigDecimal getTotalIngredientCost(String userId, LocalDate startDate, LocalDate endDate);
    Map<String, BigDecimal> getIngredientRanking(String userId, LocalDate startDate, LocalDate endDate);
}
package org.goodbye.service;

import org.goodbye.common.dto.ExpenseRecordDTO;
import org.goodbye.entity.ExpenseRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExpenseService {
    ExpenseRecord addExpense(String userId, ExpenseRecordDTO dto);
    ExpenseRecord updateExpense(String id, String userId, ExpenseRecordDTO dto);
    void deleteExpense(String id, String userId);
    List<ExpenseRecord> getExpensesByDateRange(String userId, LocalDate startDate, LocalDate endDate);
    List<ExpenseRecord> getExpensesByDate(String userId, LocalDate date);
    Map<String, BigDecimal> getCategoryBreakdown(String userId, LocalDate startDate, LocalDate endDate);
    BigDecimal getTotalExpense(String userId, LocalDate date);
    BigDecimal getTotalExpenseByDateRange(String userId, LocalDate startDate, LocalDate endDate);
    BigDecimal getIngredientCostByDateRange(String userId, LocalDate startDate, LocalDate endDate);
    BigDecimal getFixedCostByDateRange(String userId, LocalDate startDate, LocalDate endDate);
}
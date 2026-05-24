package org.goodbye.controller;

import org.goodbye.common.Result;
import org.goodbye.common.dto.ExpenseRecordDTO;
import org.goodbye.entity.ExpenseRecord;
import org.goodbye.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/add")
    public Result<ExpenseRecord> addExpense(@RequestBody ExpenseRecordDTO dto, @RequestHeader("X-User-Id") String userId) {
        ExpenseRecord record = expenseService.addExpense(userId, dto);
        return Result.success(record);
    }

    @PutMapping("/update/{id}")
    public Result<ExpenseRecord> updateExpense(@PathVariable String id, @RequestBody ExpenseRecordDTO dto, @RequestHeader("X-User-Id") String userId) {
        ExpenseRecord record = expenseService.updateExpense(id, userId, dto);
        if (record == null) {
            return Result.error("Expense record not found");
        }
        return Result.success(record);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteExpense(@PathVariable String id, @RequestHeader("X-User-Id") String userId) {
        expenseService.deleteExpense(id, userId);
        return Result.success(null);
    }

    @GetMapping("/list")
    public Result<List<ExpenseRecord>> getExpenses(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ExpenseRecord> records = expenseService.getExpensesByDateRange(userId, startDate, endDate);
        return Result.success(records);
    }

    @GetMapping("/list/by-date")
    public Result<List<ExpenseRecord>> getExpensesByDate(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ExpenseRecord> records = expenseService.getExpensesByDate(userId, date);
        return Result.success(records);
    }

    @GetMapping("/total")
    public Result<BigDecimal> getTotalExpense(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal total = expenseService.getTotalExpenseByDateRange(userId, startDate, endDate);
        return Result.success(total);
    }

    @GetMapping("/category-breakdown")
    public Result<Map<String, BigDecimal>> getCategoryBreakdown(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, BigDecimal> breakdown = expenseService.getCategoryBreakdown(userId, startDate, endDate);
        return Result.success(breakdown);
    }
}
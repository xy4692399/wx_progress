package org.goodbye.controller;

import org.goodbye.common.Result;
import org.goodbye.common.dto.IngredientRecordDTO;
import org.goodbye.entity.IngredientRecord;
import org.goodbye.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/add")
    public Result<IngredientRecord> addIngredient(@RequestBody IngredientRecordDTO dto, @RequestHeader("X-User-Id") String userId) {
        IngredientRecord record = ingredientService.addIngredient(userId, dto);
        return Result.success(record);
    }

    @PutMapping("/update/{id}")
    public Result<IngredientRecord> updateIngredient(@PathVariable String id, @RequestBody IngredientRecordDTO dto, @RequestHeader("X-User-Id") String userId) {
        IngredientRecord record = ingredientService.updateIngredient(id, userId, dto);
        if (record == null) {
            return Result.error("Ingredient record not found");
        }
        return Result.success(record);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteIngredient(@PathVariable String id, @RequestHeader("X-User-Id") String userId) {
        ingredientService.deleteIngredient(id, userId);
        return Result.success(null);
    }

    @GetMapping("/list")
    public Result<List<IngredientRecord>> getIngredients(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<IngredientRecord> records;
        if (startDate != null && endDate != null) {
            records = ingredientService.getIngredientsByDateRange(userId, startDate, endDate);
        } else {
            records = ingredientService.getAllIngredients(userId);
        }
        return Result.success(records);
    }

    @GetMapping("/total")
    public Result<BigDecimal> getTotalIngredientCost(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal total = ingredientService.getTotalIngredientCost(userId, startDate, endDate);
        return Result.success(total);
    }

    @GetMapping("/ranking")
    public Result<Map<String, BigDecimal>> getIngredientRanking(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, BigDecimal> ranking = ingredientService.getIngredientRanking(userId, startDate, endDate);
        return Result.success(ranking);
    }
}
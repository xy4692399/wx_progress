package org.goodbye.controller;

import org.goodbye.common.Result;
import org.goodbye.common.dto.RevenueRecordDTO;
import org.goodbye.entity.RevenueRecord;
import org.goodbye.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/revenue")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @PostMapping("/add")
    public Result<RevenueRecord> addRevenue(@RequestBody RevenueRecordDTO dto, @RequestHeader("X-User-Id") String userId) {
        RevenueRecord record = revenueService.addRevenue(userId, dto);
        return Result.success(record);
    }

    @PutMapping("/update/{id}")
    public Result<RevenueRecord> updateRevenue(@PathVariable String id, @RequestBody RevenueRecordDTO dto, @RequestHeader("X-User-Id") String userId) {
        RevenueRecord record = revenueService.updateRevenue(id, userId, dto);
        if (record == null) {
            return Result.error("Revenue record not found");
        }
        return Result.success(record);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteRevenue(@PathVariable String id, @RequestHeader("X-User-Id") String userId) {
        revenueService.deleteRevenue(id, userId);
        return Result.success(null);
    }

    @GetMapping("/list")
    public Result<List<RevenueRecord>> getRevenues(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<RevenueRecord> records = revenueService.getRevenuesByDateRange(userId, startDate, endDate);
        return Result.success(records);
    }

    @GetMapping("/list/by-date")
    public Result<List<RevenueRecord>> getRevenuesByDate(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<RevenueRecord> records = revenueService.getRevenuesByDate(userId, date);
        return Result.success(records);
    }

    @GetMapping("/total")
    public Result<BigDecimal> getTotalRevenue(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal total = revenueService.getTotalRevenueByDateRange(userId, startDate, endDate);
        return Result.success(total);
    }

    @GetMapping("/channel-breakdown")
    public Result<Map<String, BigDecimal>> getChannelBreakdown(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, BigDecimal> breakdown = revenueService.getChannelBreakdown(userId, startDate, endDate);
        return Result.success(breakdown);
    }
}
package org.goodbye.controller;

import org.goodbye.common.Result;
import org.goodbye.common.dto.DailySummaryDTO;
import org.goodbye.common.dto.MonthlySummaryDTO;
import org.goodbye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/daily")
    public Result<DailySummaryDTO> getDailySummary(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        DailySummaryDTO summary = reportService.getDailySummary(userId, date);
        return Result.success(summary);
    }

    @GetMapping("/daily/range")
    public Result<List<DailySummaryDTO>> getDailySummaries(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DailySummaryDTO> summaries = reportService.getDailySummaries(userId, startDate, endDate);
        return Result.success(summaries);
    }

    @GetMapping("/monthly")
    public Result<MonthlySummaryDTO> getMonthlySummary(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam int year,
            @RequestParam int month) {
        MonthlySummaryDTO summary = reportService.getMonthlySummary(userId, year, month);
        return Result.success(summary);
    }

    @GetMapping("/weekly")
    public Result<List<MonthlySummaryDTO>> getWeeklySummary(
            @RequestHeader("X-User-Id") String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate weekStart) {
        List<MonthlySummaryDTO> summaries = reportService.getWeeklySummary(userId, weekStart);
        return Result.success(summaries);
    }
}
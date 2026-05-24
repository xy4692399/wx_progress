package org.goodbye.service;

import org.goodbye.common.dto.DailySummaryDTO;
import org.goodbye.common.dto.MonthlySummaryDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    DailySummaryDTO getDailySummary(String userId, LocalDate date);
    List<DailySummaryDTO> getDailySummaries(String userId, LocalDate startDate, LocalDate endDate);
    MonthlySummaryDTO getMonthlySummary(String userId, int year, int month);
    List<MonthlySummaryDTO> getWeeklySummary(String userId, LocalDate weekStart);
}
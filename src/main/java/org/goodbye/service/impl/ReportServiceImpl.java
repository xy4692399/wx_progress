package org.goodbye.service.impl;

import org.goodbye.common.dto.DailySummaryDTO;
import org.goodbye.common.dto.MonthlySummaryDTO;
import org.goodbye.service.ExpenseService;
import org.goodbye.service.RevenueService;
import org.goodbye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private RevenueService revenueService;

    @Autowired
    private ExpenseService expenseService;

    @Override
    public DailySummaryDTO getDailySummary(String userId, LocalDate date) {
        DailySummaryDTO summary = new DailySummaryDTO();
        summary.setSummaryDate(date);

        BigDecimal totalRevenue = revenueService.getTotalRevenue(userId, date);
        summary.setTotalRevenue(totalRevenue);

        BigDecimal totalExpense = expenseService.getTotalExpense(userId, date);
        summary.setTotalExpense(totalExpense);

        BigDecimal ingredientCost = expenseService.getIngredientCostByDateRange(userId, date, date);
        summary.setIngredientCost(ingredientCost);

        BigDecimal fixedCost = expenseService.getFixedCostByDateRange(userId, date, date);
        BigDecimal netProfit = totalRevenue.subtract(totalExpense);
        summary.setNetProfit(netProfit);

        Map<String, BigDecimal> channelBreakdown = revenueService.getChannelBreakdown(userId, date, date);
        summary.setChannelBreakdown(channelBreakdown);

        return summary;
    }

    @Override
    public List<DailySummaryDTO> getDailySummaries(String userId, LocalDate startDate, LocalDate endDate) {
        List<DailySummaryDTO> summaries = new ArrayList<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            summaries.add(getDailySummary(userId, current));
            current = current.plusDays(1);
        }
        return summaries;
    }

    @Override
    public MonthlySummaryDTO getMonthlySummary(String userId, int year, int month) {
        MonthlySummaryDTO summary = new MonthlySummaryDTO();
        summary.setYear(year);
        summary.setMonth(month);

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        BigDecimal totalRevenue = revenueService.getTotalRevenueByDateRange(userId, startDate, endDate);
        summary.setTotalRevenue(totalRevenue);

        BigDecimal totalExpense = expenseService.getTotalExpenseByDateRange(userId, startDate, endDate);
        summary.setTotalExpense(totalExpense);

        BigDecimal ingredientCost = expenseService.getIngredientCostByDateRange(userId, startDate, endDate);
        summary.setIngredientCost(ingredientCost);

        BigDecimal fixedCost = expenseService.getFixedCostByDateRange(userId, startDate, endDate);
        summary.setFixedCost(fixedCost);

        BigDecimal netProfit = totalRevenue.subtract(totalExpense);
        summary.setNetProfit(netProfit);

        if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal fixedCostRatio = fixedCost.divide(totalRevenue, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            summary.setFixedCostRatio(fixedCostRatio);

            BigDecimal ingredientCostRatio = ingredientCost.divide(totalRevenue, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            summary.setIngredientCostRatio(ingredientCostRatio);
        } else {
            summary.setFixedCostRatio(BigDecimal.ZERO);
            summary.setIngredientCostRatio(BigDecimal.ZERO);
        }

        Map<String, BigDecimal> channelBreakdown = revenueService.getChannelBreakdown(userId, startDate, endDate);
        summary.setChannelBreakdown(channelBreakdown);

        Map<String, BigDecimal> expenseCategoryBreakdown = expenseService.getCategoryBreakdown(userId, startDate, endDate);
        summary.setExpenseCategoryBreakdown(expenseCategoryBreakdown);

        List<DailySummaryDTO> dailySummaries = getDailySummaries(userId, startDate, endDate);
        summary.setDailySummaries(dailySummaries);

        return summary;
    }

    @Override
    public List<MonthlySummaryDTO> getWeeklySummary(String userId, LocalDate weekStart) {
        LocalDate weekEnd = weekStart.plusDays(6);
        MonthlySummaryDTO summary = new MonthlySummaryDTO();
        summary.setYear(weekStart.getYear());
        summary.setMonth(weekStart.getMonthValue());

        BigDecimal totalRevenue = revenueService.getTotalRevenueByDateRange(userId, weekStart, weekEnd);
        summary.setTotalRevenue(totalRevenue);

        BigDecimal totalExpense = expenseService.getTotalExpenseByDateRange(userId, weekStart, weekEnd);
        summary.setTotalExpense(totalExpense);

        BigDecimal ingredientCost = expenseService.getIngredientCostByDateRange(userId, weekStart, weekEnd);
        summary.setIngredientCost(ingredientCost);

        BigDecimal fixedCost = expenseService.getFixedCostByDateRange(userId, weekStart, weekEnd);
        summary.setFixedCost(fixedCost);

        BigDecimal netProfit = totalRevenue.subtract(totalExpense);
        summary.setNetProfit(netProfit);

        Map<String, BigDecimal> channelBreakdown = revenueService.getChannelBreakdown(userId, weekStart, weekEnd);
        summary.setChannelBreakdown(channelBreakdown);

        List<DailySummaryDTO> dailySummaries = getDailySummaries(userId, weekStart, weekEnd);
        summary.setDailySummaries(dailySummaries);

        List<MonthlySummaryDTO> result = new ArrayList<>();
        result.add(summary);
        return result;
    }
}
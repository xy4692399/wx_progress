package org.goodbye.service;

import org.goodbye.common.dto.RevenueRecordDTO;
import org.goodbye.entity.RevenueRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RevenueService {
    RevenueRecord addRevenue(String userId, RevenueRecordDTO dto);
    RevenueRecord updateRevenue(String id, String userId, RevenueRecordDTO dto);
    void deleteRevenue(String id, String userId);
    List<RevenueRecord> getRevenuesByDateRange(String userId, LocalDate startDate, LocalDate endDate);
    List<RevenueRecord> getRevenuesByDate(String userId, LocalDate date);
    Map<String, BigDecimal> getChannelBreakdown(String userId, LocalDate startDate, LocalDate endDate);
    BigDecimal getTotalRevenue(String userId, LocalDate date);
    BigDecimal getTotalRevenueByDateRange(String userId, LocalDate startDate, LocalDate endDate);
}
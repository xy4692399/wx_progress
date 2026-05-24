package org.goodbye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.goodbye.common.dto.RevenueRecordDTO;
import org.goodbye.entity.RevenueRecord;
import org.goodbye.mapper.RevenueRecordMapper;
import org.goodbye.service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RevenueServiceImpl implements RevenueService {

    @Autowired
    private RevenueRecordMapper revenueRecordMapper;

    @Override
    public RevenueRecord addRevenue(String userId, RevenueRecordDTO dto) {
        RevenueRecord record = new RevenueRecord();
        record.setUserId(userId);
        record.setRecordDate(dto.getRecordDate());
        record.setChannel(dto.getChannel());
        record.setAmount(dto.getAmount());
        record.setRemark(dto.getRemark());
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        revenueRecordMapper.insert(record);
        return record;
    }

    @Override
    public RevenueRecord updateRevenue(String id, String userId, RevenueRecordDTO dto) {
        RevenueRecord record = getRevenueById(id, userId);
        if (record != null) {
            record.setRecordDate(dto.getRecordDate());
            record.setChannel(dto.getChannel());
            record.setAmount(dto.getAmount());
            record.setRemark(dto.getRemark());
            record.setUpdateTime(LocalDateTime.now());
            revenueRecordMapper.updateById(record);
        }
        return record;
    }

    @Override
    public void deleteRevenue(String id, String userId) {
        RevenueRecord record = getRevenueById(id, userId);
        if (record != null) {
            revenueRecordMapper.deleteById(id);
        }
    }

    @Override
    public List<RevenueRecord> getRevenuesByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<RevenueRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RevenueRecord::getUserId, userId)
                .ge(RevenueRecord::getRecordDate, startDate)
                .le(RevenueRecord::getRecordDate, endDate)
                .orderByDesc(RevenueRecord::getRecordDate);
        return revenueRecordMapper.selectList(wrapper);
    }

    @Override
    public List<RevenueRecord> getRevenuesByDate(String userId, LocalDate date) {
        return getRevenuesByDateRange(userId, date, date);
    }

    @Override
    public Map<String, BigDecimal> getChannelBreakdown(String userId, LocalDate startDate, LocalDate endDate) {
        List<RevenueRecord> records = getRevenuesByDateRange(userId, startDate, endDate);
        Map<String, BigDecimal> breakdown = new HashMap<>();
        for (RevenueRecord record : records) {
            breakdown.merge(record.getChannel(), record.getAmount(), BigDecimal::add);
        }
        return breakdown;
    }

    @Override
    public BigDecimal getTotalRevenue(String userId, LocalDate date) {
        List<RevenueRecord> records = getRevenuesByDate(userId, date);
        return records.stream()
                .map(RevenueRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalRevenueByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        List<RevenueRecord> records = getRevenuesByDateRange(userId, startDate, endDate);
        return records.stream()
                .map(RevenueRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private RevenueRecord getRevenueById(String id, String userId) {
        LambdaQueryWrapper<RevenueRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RevenueRecord::getId, id).eq(RevenueRecord::getUserId, userId);
        return revenueRecordMapper.selectOne(wrapper);
    }
}
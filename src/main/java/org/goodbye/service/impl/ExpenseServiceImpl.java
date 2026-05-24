package org.goodbye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.goodbye.common.dto.ExpenseRecordDTO;
import org.goodbye.entity.ExpenseRecord;
import org.goodbye.mapper.ExpenseRecordMapper;
import org.goodbye.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private static final List<String> FIXED_COST_CATEGORIES = List.of(
            "房租物业", "员工工资/提成"
    );

    private static final List<String> INGREDIENT_CATEGORIES = List.of(
            "食材采购"
    );

    @Autowired
    private ExpenseRecordMapper expenseRecordMapper;

    @Override
    public ExpenseRecord addExpense(String userId, ExpenseRecordDTO dto) {
        ExpenseRecord record = new ExpenseRecord();
        record.setUserId(userId);
        record.setRecordDate(dto.getRecordDate());
        record.setCategory(dto.getCategory());
        record.setAmount(dto.getAmount());
        record.setTicketUrl(dto.getTicketUrl());
        record.setRemark(dto.getRemark());
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        expenseRecordMapper.insert(record);
        return record;
    }

    @Override
    public ExpenseRecord updateExpense(String id, String userId, ExpenseRecordDTO dto) {
        ExpenseRecord record = getExpenseById(id, userId);
        if (record != null) {
            record.setRecordDate(dto.getRecordDate());
            record.setCategory(dto.getCategory());
            record.setAmount(dto.getAmount());
            record.setTicketUrl(dto.getTicketUrl());
            record.setRemark(dto.getRemark());
            record.setUpdateTime(LocalDateTime.now());
            expenseRecordMapper.updateById(record);
        }
        return record;
    }

    @Override
    public void deleteExpense(String id, String userId) {
        ExpenseRecord record = getExpenseById(id, userId);
        if (record != null) {
            expenseRecordMapper.deleteById(id);
        }
    }

    @Override
    public List<ExpenseRecord> getExpensesByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<ExpenseRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExpenseRecord::getUserId, userId)
                .ge(ExpenseRecord::getRecordDate, startDate)
                .le(ExpenseRecord::getRecordDate, endDate)
                .orderByDesc(ExpenseRecord::getRecordDate);
        return expenseRecordMapper.selectList(wrapper);
    }

    @Override
    public List<ExpenseRecord> getExpensesByDate(String userId, LocalDate date) {
        return getExpensesByDateRange(userId, date, date);
    }

    @Override
    public Map<String, BigDecimal> getCategoryBreakdown(String userId, LocalDate startDate, LocalDate endDate) {
        List<ExpenseRecord> records = getExpensesByDateRange(userId, startDate, endDate);
        Map<String, BigDecimal> breakdown = new HashMap<>();
        for (ExpenseRecord record : records) {
            breakdown.merge(record.getCategory(), record.getAmount(), BigDecimal::add);
        }
        return breakdown;
    }

    @Override
    public BigDecimal getTotalExpense(String userId, LocalDate date) {
        List<ExpenseRecord> records = getExpensesByDate(userId, date);
        return records.stream()
                .map(ExpenseRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalExpenseByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        List<ExpenseRecord> records = getExpensesByDateRange(userId, startDate, endDate);
        return records.stream()
                .map(ExpenseRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getIngredientCostByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        List<ExpenseRecord> records = getExpensesByDateRange(userId, startDate, endDate);
        return records.stream()
                .filter(r -> INGREDIENT_CATEGORIES.contains(r.getCategory()))
                .map(ExpenseRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getFixedCostByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        List<ExpenseRecord> records = getExpensesByDateRange(userId, startDate, endDate);
        return records.stream()
                .filter(r -> FIXED_COST_CATEGORIES.contains(r.getCategory()))
                .map(ExpenseRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ExpenseRecord getExpenseById(String id, String userId) {
        LambdaQueryWrapper<ExpenseRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ExpenseRecord::getId, id).eq(ExpenseRecord::getUserId, userId);
        return expenseRecordMapper.selectOne(wrapper);
    }
}
package org.goodbye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.goodbye.common.dto.IngredientRecordDTO;
import org.goodbye.entity.IngredientRecord;
import org.goodbye.mapper.IngredientRecordMapper;
import org.goodbye.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRecordMapper ingredientRecordMapper;

    @Override
    public IngredientRecord addIngredient(String userId, IngredientRecordDTO dto) {
        IngredientRecord record = new IngredientRecord();
        record.setUserId(userId);
        record.setIngredientName(dto.getIngredientName());
        record.setPurchaseDate(dto.getPurchaseDate());
        record.setUnitPrice(dto.getUnitPrice());
        record.setQuantity(dto.getQuantity());
        record.setTotalPrice(dto.getTotalPrice());
        record.setSupplier(dto.getSupplier());
        record.setCreateTime(LocalDateTime.now());
        record.setUpdateTime(LocalDateTime.now());
        ingredientRecordMapper.insert(record);
        return record;
    }

    @Override
    public IngredientRecord updateIngredient(String id, String userId, IngredientRecordDTO dto) {
        IngredientRecord record = getIngredientById(id, userId);
        if (record != null) {
            record.setIngredientName(dto.getIngredientName());
            record.setPurchaseDate(dto.getPurchaseDate());
            record.setUnitPrice(dto.getUnitPrice());
            record.setQuantity(dto.getQuantity());
            record.setTotalPrice(dto.getTotalPrice());
            record.setSupplier(dto.getSupplier());
            record.setUpdateTime(LocalDateTime.now());
            ingredientRecordMapper.updateById(record);
        }
        return record;
    }

    @Override
    public void deleteIngredient(String id, String userId) {
        IngredientRecord record = getIngredientById(id, userId);
        if (record != null) {
            ingredientRecordMapper.deleteById(id);
        }
    }

    @Override
    public List<IngredientRecord> getIngredientsByDateRange(String userId, LocalDate startDate, LocalDate endDate) {
        LambdaQueryWrapper<IngredientRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IngredientRecord::getUserId, userId)
                .ge(IngredientRecord::getPurchaseDate, startDate)
                .le(IngredientRecord::getPurchaseDate, endDate)
                .orderByDesc(IngredientRecord::getPurchaseDate);
        return ingredientRecordMapper.selectList(wrapper);
    }

    @Override
    public List<IngredientRecord> getAllIngredients(String userId) {
        LambdaQueryWrapper<IngredientRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IngredientRecord::getUserId, userId)
                .orderByDesc(IngredientRecord::getPurchaseDate);
        return ingredientRecordMapper.selectList(wrapper);
    }

    @Override
    public BigDecimal getTotalIngredientCost(String userId, LocalDate startDate, LocalDate endDate) {
        List<IngredientRecord> records = getIngredientsByDateRange(userId, startDate, endDate);
        return records.stream()
                .map(IngredientRecord::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Map<String, BigDecimal> getIngredientRanking(String userId, LocalDate startDate, LocalDate endDate) {
        List<IngredientRecord> records = getIngredientsByDateRange(userId, startDate, endDate);
        Map<String, BigDecimal> costMap = new HashMap<>();
        for (IngredientRecord record : records) {
            costMap.merge(record.getIngredientName(), record.getTotalPrice(), BigDecimal::add);
        }
        return costMap.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        HashMap::new
                ));
    }

    private IngredientRecord getIngredientById(String id, String userId) {
        LambdaQueryWrapper<IngredientRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(IngredientRecord::getId, id).eq(IngredientRecord::getUserId, userId);
        return ingredientRecordMapper.selectOne(wrapper);
    }
}
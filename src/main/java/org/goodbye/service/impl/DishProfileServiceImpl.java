package org.goodbye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.goodbye.common.dto.DishProfileDTO;
import org.goodbye.entity.DishProfile;
import org.goodbye.mapper.DishProfileMapper;
import org.goodbye.service.DishProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishProfileServiceImpl implements DishProfileService {

    @Autowired
    private DishProfileMapper dishProfileMapper;

    @Override
    public DishProfile addDish(String userId, DishProfileDTO dto) {
        DishProfile dish = new DishProfile();
        dish.setUserId(userId);
        dish.setDishName(dto.getDishName());
        dish.setSellingPrice(dto.getSellingPrice());
        dish.setIngredientCost(dto.getIngredientCost());
        dish.setDailyAverageSales(dto.getDailyAverageSales());
        dish.setCreateTime(LocalDateTime.now());
        dish.setUpdateTime(LocalDateTime.now());
        dishProfileMapper.insert(dish);
        return dish;
    }

    @Override
    public DishProfile updateDish(String id, String userId, DishProfileDTO dto) {
        DishProfile dish = getDishById(id, userId);
        if (dish != null) {
            dish.setDishName(dto.getDishName());
            dish.setSellingPrice(dto.getSellingPrice());
            dish.setIngredientCost(dto.getIngredientCost());
            dish.setDailyAverageSales(dto.getDailyAverageSales());
            dish.setUpdateTime(LocalDateTime.now());
            dishProfileMapper.updateById(dish);
        }
        return dish;
    }

    @Override
    public void deleteDish(String id, String userId) {
        DishProfile dish = getDishById(id, userId);
        if (dish != null) {
            dishProfileMapper.deleteById(id);
        }
    }

    @Override
    public List<DishProfile> getAllDishes(String userId) {
        LambdaQueryWrapper<DishProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishProfile::getUserId, userId)
                .orderByDesc(DishProfile::getCreateTime);
        return dishProfileMapper.selectList(wrapper);
    }

    @Override
    public List<DishProfileDTO> getDishProfiles(String userId) {
        List<DishProfile> dishes = getAllDishes(userId);
        return dishes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private DishProfileDTO convertToDTO(DishProfile dish) {
        DishProfileDTO dto = new DishProfileDTO();
        dto.setId(dish.getId());
        dto.setDishName(dish.getDishName());
        dto.setSellingPrice(dish.getSellingPrice());
        dto.setIngredientCost(dish.getIngredientCost());
        dto.setDailyAverageSales(dish.getDailyAverageSales());

        BigDecimal profit = dish.getSellingPrice().subtract(dish.getIngredientCost());
        dto.setProfit(profit);

        if (dish.getSellingPrice().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal profitRate = profit.divide(dish.getSellingPrice(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            dto.setProfitRate(profitRate);
        } else {
            dto.setProfitRate(BigDecimal.ZERO);
        }

        if (dish.getDailyAverageSales() != null) {
            dto.setEstimatedDailyProfit(profit.multiply(dish.getDailyAverageSales()));
        } else {
            dto.setEstimatedDailyProfit(BigDecimal.ZERO);
        }

        return dto;
    }

    private DishProfile getDishById(String id, String userId) {
        LambdaQueryWrapper<DishProfile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishProfile::getId, id).eq(DishProfile::getUserId, userId);
        return dishProfileMapper.selectOne(wrapper);
    }
}
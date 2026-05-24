package org.goodbye.controller;

import org.goodbye.common.Result;
import org.goodbye.common.dto.DishProfileDTO;
import org.goodbye.entity.DishProfile;
import org.goodbye.service.DishProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishProfileController {

    @Autowired
    private DishProfileService dishProfileService;

    @PostMapping("/add")
    public Result<DishProfile> addDish(@RequestBody DishProfileDTO dto, @RequestHeader("X-User-Id") String userId) {
        DishProfile dish = dishProfileService.addDish(userId, dto);
        return Result.success(dish);
    }

    @PutMapping("/update/{id}")
    public Result<DishProfile> updateDish(@PathVariable String id, @RequestBody DishProfileDTO dto, @RequestHeader("X-User-Id") String userId) {
        DishProfile dish = dishProfileService.updateDish(id, userId, dto);
        if (dish == null) {
            return Result.error("Dish profile not found");
        }
        return Result.success(dish);
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteDish(@PathVariable String id, @RequestHeader("X-User-Id") String userId) {
        dishProfileService.deleteDish(id, userId);
        return Result.success(null);
    }

    @GetMapping("/list")
    public Result<List<DishProfile>> getAllDishes(@RequestHeader("X-User-Id") String userId) {
        List<DishProfile> dishes = dishProfileService.getAllDishes(userId);
        return Result.success(dishes);
    }

    @GetMapping("/profit-analysis")
    public Result<List<DishProfileDTO>> getDishProfitAnalysis(@RequestHeader("X-User-Id") String userId) {
        List<DishProfileDTO> dishes = dishProfileService.getDishProfiles(userId);
        return Result.success(dishes);
    }
}
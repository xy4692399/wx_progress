package org.goodbye.service;

import org.goodbye.common.dto.DishProfileDTO;
import org.goodbye.entity.DishProfile;

import java.util.List;

public interface DishProfileService {
    DishProfile addDish(String userId, DishProfileDTO dto);
    DishProfile updateDish(String id, String userId, DishProfileDTO dto);
    void deleteDish(String id, String userId);
    List<DishProfile> getAllDishes(String userId);
    List<DishProfileDTO> getDishProfiles(String userId);
}
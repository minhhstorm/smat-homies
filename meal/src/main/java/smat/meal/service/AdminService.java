package smat.meal.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import smat.meal.dto.AddDishRequestDTO;
import smat.meal.dto.AddIngredientRequestDTO;
import smat.meal.dto.ParticipantDTO;
import smat.meal.entity.DishEntity;
import smat.meal.entity.IngredientEntity;
import smat.meal.repository.DishRepository;
import smat.meal.repository.IngredientRepository;
import smat.meal.repository.ParticipantRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@AllArgsConstructor
public class AdminService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;
    private final ParticipantRepository participantRepository;


    public void insertIngredient(AddIngredientRequestDTO addIngredientRequestDTO) {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setName(addIngredientRequestDTO.getName());
        ingredientEntity.setAddress(addIngredientRequestDTO.getAddress());
        ingredientEntity.setDescription(addIngredientRequestDTO.getDescription());
        ingredientEntity.setPrice(addIngredientRequestDTO.getPrice());
        ingredientEntity.setType(addIngredientRequestDTO.getType());
        ingredientRepository.save(ingredientEntity);
    }

    public void addDish(AddDishRequestDTO addDishRequestDTO) {
        DishEntity dishEntity = new DishEntity();
        dishEntity.setName(addDishRequestDTO.getName());
        dishEntity.setDescription(addDishRequestDTO.getDescription());
        dishEntity.setType(addDishRequestDTO.getType());
        Set<IngredientEntity> ingredients = new HashSet<>();

        int size = addDishRequestDTO.getIngredients().size();
        for (int i = 0; i < size; i++) {
            IngredientEntity ingredientEntity = new IngredientEntity();
            ingredientEntity.setId(addDishRequestDTO.getIngredients().get(i));
            ingredients.add(ingredientEntity);
        }
        dishEntity.setIngredients(ingredients);
        dishRepository.save(dishEntity);
    }

    public List<IngredientEntity> getAllIngredient() {
        return ingredientRepository.findAll();
    }


    public void registerMeal(ParticipantDTO participantDTO) {
        LocalDate day = LocalDate.now();
        String[] timeArray = participantDTO.getTimeArrived().split(":");
        LocalTime hour = LocalTime.of(Integer.parseInt(timeArray[0]), Integer.parseInt(timeArray[1]));
        LocalDateTime timeArrived = LocalDateTime.of(day, hour);


    }
}

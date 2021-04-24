package smat.meal.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import smat.meal.dto.AddDishRequestDTO;
import smat.meal.dto.AddIngredientRequestDTO;
import smat.meal.entity.DishEntity;
import smat.meal.entity.IngredientEntity;
import smat.meal.repository.DishRepository;
import smat.meal.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;


    public void insertIngredient(AddIngredientRequestDTO addIngredientRequestDTO) {
        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setName(addIngredientRequestDTO.getName());
        ingredientEntity.setAddress(addIngredientRequestDTO.getAddress());
        ingredientEntity.setDescription(addIngredientRequestDTO.getDescription());
        ingredientEntity.setPrice(addIngredientRequestDTO.getPrice());
        ingredientEntity.setType(addIngredientRequestDTO.getType());
        ingredientRepository.save(ingredientEntity);
    }

    public void insertDish(AddDishRequestDTO addDishRequestDTO) {
        DishEntity dishEntity = new DishEntity();
        IngredientEntity ingredientEntity = new IngredientEntity();
        dishEntity.setName(addDishRequestDTO.getName());
        dishEntity.setDescription(addDishRequestDTO.getDescription());
        dishEntity.setType(addDishRequestDTO.getType());
        List<IngredientEntity> ingredients = new ArrayList<>();

        int size = addDishRequestDTO.getIngredients().size();
        for (int i = 0; i < size; i++) {
            ingredientEntity = ingredientRepository.findByName(addDishRequestDTO.getIngredients().get(i + 1));
            ingredients.add(ingredientEntity);
        }
        dishEntity.setIngredients(ingredients);
        dishRepository.save(dishEntity);
    }

    public List<IngredientEntity> getAllIngredient() {
        return ingredientRepository.findAll();
    }

    public List<DishEntity> getAllDish() {
        return  dishRepository.findAll();
    }
}

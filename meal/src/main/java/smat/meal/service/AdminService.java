package smat.meal.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import smat.meal.dto.AddDishRequestDTO;
import smat.meal.dto.AddIngredientRequestDTO;
import smat.meal.entity.DishEntity;
import smat.meal.entity.IngredientEntity;
import smat.meal.repository.DishRepository;
import smat.meal.repository.IngredientRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        System.out.println(dishEntity.toString());
        dishRepository.save(dishEntity);
    }

    public List<IngredientEntity> getAllIngredient() {
        return ingredientRepository.findAll();
    }

    public List<DishEntity> getAllDish() {
        return  dishRepository.findAll();
    }
}

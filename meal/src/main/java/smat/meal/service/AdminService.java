package smat.meal.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smat.meal.dto.AddIngredientDishDTO;
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

    @Transactional
    public void createDish(AddIngredientDishDTO addIngredientDishDTO) {
        DishEntity dishEntity = new DishEntity();
        IngredientEntity ingredientEntity = new IngredientEntity();

        dishEntity.setName(addIngredientDishDTO.getNameDish());
        dishEntity.setDescription(addIngredientDishDTO.getDescriptionDish());
        dishEntity.setType(addIngredientDishDTO.getTypeDish());

        ingredientEntity.setName(addIngredientDishDTO.getNameIngredient());
        ingredientEntity.setPrice(addIngredientDishDTO.getPriceIngredient());
        ingredientEntity.setAddress(addIngredientDishDTO.getAddressIngredient());
        ingredientEntity.setDescription(addIngredientDishDTO.getDescriptionIngredient());
        ingredientEntity.setType(addIngredientDishDTO.getTypeIngredient());

        List<IngredientEntity> ingredients = new ArrayList<>();
        ingredients.add(ingredientEntity);
        dishEntity.setIngredients(ingredients);
        dishRepository.save(dishEntity);
        ingredientRepository.save(ingredientEntity);
    }
}

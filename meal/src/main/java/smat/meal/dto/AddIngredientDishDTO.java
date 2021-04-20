package smat.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddIngredientDishDTO {
    private String nameDish;
    private String descriptionDish;
    private int typeDish;

    private String nameIngredient;
    private String addressIngredient;
    private String descriptionIngredient;
    private int priceIngredient;
    private int typeIngredient;
}

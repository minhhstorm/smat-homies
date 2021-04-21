package smat.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import smat.meal.entity.IngredientEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDishRequestDTO {
    private String name;
    private String description;
    private int type;

    List<Long> ingredients;
}

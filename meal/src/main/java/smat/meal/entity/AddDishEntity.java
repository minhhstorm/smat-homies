package smat.meal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddDishEntity {
    private Long ingredientId;
    private Long weight;
}

package smat.meal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dish_ingredient")
public class IngredientDishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dish_id")
    private Long dishId;

    @Column(name = "ingredient_id")
    private Long ingredientId;

    @Column(name = "weight_ingredient")
    private Long weight;
}

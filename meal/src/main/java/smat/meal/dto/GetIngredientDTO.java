package smat.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetIngredientDTO {
    private int id;
    private String address;
    private String description;
    private String name;
    private int price;
    private int type;
}

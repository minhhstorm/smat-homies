package smat.meal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDishRequestDTO {
    private String name;
    private String description;
    private int type;

    List<String> ingredients = new ArrayList<>();
}

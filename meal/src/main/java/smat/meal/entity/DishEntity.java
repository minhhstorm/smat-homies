package smat.meal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dish")
public class DishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private int type;

    @Lob
    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ingredient_dish",
                    joinColumns = @JoinColumn(name = "ingredient_id")
                    , inverseJoinColumns = @JoinColumn(name = "dish_id"))
    private Set<IngredientEntity> ingredients = new HashSet<>();
}

package smat.meal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredient")
public class IngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "type")
    private int type;

    @ManyToMany(mappedBy = "ingredients")
    @JsonIgnore
    private Set<DishEntity> dishs = new HashSet<>();
}

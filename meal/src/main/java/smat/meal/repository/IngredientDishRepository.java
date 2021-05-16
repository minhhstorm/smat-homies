package smat.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smat.meal.entity.IngredientDishEntity;

import java.util.List;

@Repository
public interface IngredientDishRepository extends JpaRepository<IngredientDishRepository, Long> {
    List<IngredientDishEntity> findByDishId(Long dishId);
}

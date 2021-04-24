package smat.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smat.meal.entity.IngredientEntity;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    Boolean existsByName(String name);
    IngredientEntity findByName(String name);

    @Query(value = "select e from IngredientEntity e")
    List<IngredientEntity> findAllss();
}

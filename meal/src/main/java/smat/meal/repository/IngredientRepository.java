package smat.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smat.meal.entity.IngredientEntity;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    Optional<IngredientEntity> findByName(String name);
}

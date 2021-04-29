package smat.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smat.meal.entity.MealEntity;

@Repository
public interface MealRepository extends JpaRepository<MealEntity, Long> {

}

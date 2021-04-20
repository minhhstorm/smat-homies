package smat.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smat.meal.entity.DishEntity;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long> {

    Optional<DishEntity> findByName(String name);
    Boolean existsByName(String name);

}

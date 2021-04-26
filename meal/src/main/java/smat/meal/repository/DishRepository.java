package smat.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smat.meal.entity.DishEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long> {

    Optional<DishEntity> findByName(String name);
    Boolean existsByName(String name);
    List<DishEntity> findAll();

    @Query(value = "SELECT * FROM dish d where d.type = ?1 ORDER BY ?2 LIMIT 1", nativeQuery=true)
    DishEntity findByType(int type, String ran);

}

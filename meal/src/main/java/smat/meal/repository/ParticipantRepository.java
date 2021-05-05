package smat.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smat.meal.entity.ParticipantEntity;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {
    List<ParticipantEntity> findAll();
}

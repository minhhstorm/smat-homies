package smat.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smat.meal.entity.TokenEntity;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByToken(String token);
}

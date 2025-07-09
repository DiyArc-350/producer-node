package com.voltras.producer.repositories;

import com.voltras.producer.entities.FCMTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FCMTokenRepository extends JpaRepository<FCMTokenEntity, Long> {
    boolean existsByToken(String token);

    @Query("SELECT f.token FROM FCMTokenEntity f")
    List<String> findAllTokens();
}

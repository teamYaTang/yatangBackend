package com.kaya.yatang.db.repository;

import com.kaya.yatang.db.entity.Fridge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {
    List<Fridge> findByUserId(Long userId);
    Optional<Fridge> findByIdAndUserId(Long fridgeId, Long userId);
}
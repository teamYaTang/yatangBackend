package com.kaya.yatang.db.repository;

import com.kaya.yatang.db.entity.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<Fridge, Long> {
    Fridge findByUserId(Long userId);
}

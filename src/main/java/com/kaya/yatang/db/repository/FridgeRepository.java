package com.kaya.yatang.db.repository;

import com.kaya.yatang.db.entity.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FridgeRepository extends JpaRepository<Fridge, Long> {
    Fridge findByUserId(Long userId);
}

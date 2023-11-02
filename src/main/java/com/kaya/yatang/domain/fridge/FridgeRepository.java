package com.kaya.yatang.domain.fridge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<FridgeEntity, Long> {
    FridgeEntity findByUserId(Long userId);
}

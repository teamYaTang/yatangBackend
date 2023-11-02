package com.kaya.yatang.domain.fridge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<FridgeEntity, String> {
    FridgeEntity findByUserId(long userId);
}

package com.kaya.yatang.db.repository;

import com.kaya.yatang.db.entity.FridgeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FridgeItemRepository extends JpaRepository<FridgeItem, Long> {

    // 특정 냉장고의 모든 아이템 조회
    List<FridgeItem> findByFridgeId(Long fridgeId);

    // 특정 냉장고의 특정 아이템 조회
    List<FridgeItem> findByFridgeIdAndNameContaining(Long fridgeId, String name);

    // 유통기한 임박 아이템 조회
    @Query("SELECT f FROM FridgeItem f WHERE f.fridge.id = :fridgeId " +
        "AND f.expirationDate IS NOT NULL " +
        "AND f.expirationDate BETWEEN :startDate AND :endDate " +
        "ORDER BY f.expirationDate ASC")
    List<FridgeItem> findExpiringSoonItems(
        @Param("fridgeId") Long fridgeId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    // 만료된 아이템 조회
    @Query("SELECT f FROM FridgeItem f WHERE f.fridge.id = :fridgeId " +
        "AND f.expirationDate IS NOT NULL " +
        "AND f.expirationDate < :today")
    List<FridgeItem> findExpiredItems(
        @Param("fridgeId") Long fridgeId,
        @Param("today") LocalDate today
    );
}
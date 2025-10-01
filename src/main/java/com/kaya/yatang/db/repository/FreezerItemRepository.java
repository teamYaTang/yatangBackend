package com.kaya.yatang.db.repository;

import com.kaya.yatang.db.entity.FreezerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FreezerItemRepository extends JpaRepository<FreezerItem, Long> {

    // 특정 냉장고의 모든 아이템 조회
    List<FreezerItem> findByFridgeId(Long fridgeId);

    // 특정 냉장고의 특정 아이템 조회
    List<FreezerItem> findByFridgeIdAndNameContaining(Long fridgeId, String name);

    // 장기 보관 아이템 조회 (6개월 이상)
    @Query("SELECT f FROM FreezerItem f WHERE f.fridge.id = :fridgeId " +
        "AND f.freezeDate IS NOT NULL " +
        "AND f.freezeDate <= :sixMonthsAgo")
    List<FreezerItem> findLongTermFrozenItems(
        @Param("fridgeId") Long fridgeId,
        @Param("sixMonthsAgo") LocalDate sixMonthsAgo
    );

    // 유통기한 임박 아이템 조회
    @Query("SELECT f FROM FreezerItem f WHERE f.fridge.id = :fridgeId " +
        "AND f.expirationDate IS NOT NULL " +
        "AND f.expirationDate BETWEEN :startDate AND :endDate " +
        "ORDER BY f.expirationDate ASC")
    List<FreezerItem> findExpiringSoonItems(
        @Param("fridgeId") Long fridgeId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );
}
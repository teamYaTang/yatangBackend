package com.kaya.yatang.db.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "freezer_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreezerItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name; // 재료명

    @Column(nullable = false)
    private Integer quantity; // 수량

    @Column(nullable = false, length = 20)
    private String unit; // 단위 (개, kg, L, g, ml 등)

    @Column(name = "expiration_date")
    private LocalDate expirationDate; // 유통기한

    @Column(name = "manufacture_date")
    private LocalDate manufactureDate; // 제조일자

    @Column(name = "freeze_date")
    private LocalDate freezeDate; // 냉동 보관 시작일

    @Column(length = 1000)
    private String memo; // 메모

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Fridge와의 관계 (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id", nullable = false)
    private Fridge fridge;

    // 생성일/수정일 자동 설정
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        // freezeDate가 없으면 현재 날짜로 설정
        if (this.freezeDate == null) {
            this.freezeDate = LocalDate.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 유틸리티 메서드 - 냉동 보관 기간 계산
    public long getFrozenDays() {
        if (freezeDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(freezeDate, LocalDate.now());
    }

    // 유틸리티 메서드 - 유통기한 D-day 계산
    public long getDaysUntilExpiration() {
        if (expirationDate == null) {
            return Long.MAX_VALUE;
        }
        return ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);
    }

    // 유틸리티 메서드 - 유통기한 임박 여부
    public boolean isExpiringSoon(int days) {
        long daysUntil = getDaysUntilExpiration();
        return daysUntil >= 0 && daysUntil <= days;
    }

    // 유틸리티 메서드 - 유통기한 만료 여부
    public boolean isExpired() {
        return getDaysUntilExpiration() < 0;
    }

    // 유틸리티 메서드 - 장기 보관 여부 (냉동 6개월 이상)
    public boolean isLongTermFrozen() {
        return getFrozenDays() >= 180;
    }

    // 정적 팩토리 메서드
    public static FreezerItem createItem(Fridge fridge, String name, Integer quantity,
        String unit, LocalDate expirationDate) {
        return FreezerItem.builder()
            .fridge(fridge)
            .name(name)
            .quantity(quantity)
            .unit(unit)
            .expirationDate(expirationDate)
            .freezeDate(LocalDate.now())
            .build();
    }
}
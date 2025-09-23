package com.kaya.yatang.db.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.*;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "freezer_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FreezerItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 재료명

    @Column(nullable = false)
    private Integer quantity; // 수량

    @Column(nullable = false)
    private String unit; // 단위

    private LocalDate expirationDate; // 유통기한

    private LocalDate manufactureDate; // 제조일자

    private LocalDate freezeDate; // 냉동 보관 시작일

    @Column(length = 1000)
    private String memo; // 메모

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // FreezerItem -> Fridge (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id", nullable = false)
    private Fridge fridge;

    // 냉동 보관 기간 계산
    public long getFrozenDays() {
        if (freezeDate == null) return 0;
        return ChronoUnit.DAYS.between(freezeDate, LocalDate.now());
    }
}
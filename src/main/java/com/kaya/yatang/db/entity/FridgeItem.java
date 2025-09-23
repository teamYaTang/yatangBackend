package com.kaya.yatang.db.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.*;

import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "fridge_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FridgeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 재료명

    @Column(nullable = false)
    private Integer quantity; // 수량

    @Column(nullable = false)
    private String unit; // 단위 (개, kg, L 등)

    private LocalDate expirationDate; // 유통기한

    private LocalDate manufactureDate; // 제조일자

    @Column(length = 1000)
    private String memo; // 메모

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // FridgeItem -> Fridge (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id", nullable = false)
    private Fridge fridge;

    // 유통기한 D-day 계산
    public long getDaysUntilExpiration() {
        if (expirationDate == null) return Long.MAX_VALUE;
        return ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);
    }

    // 유통기한 임박 여부
    public boolean isExpiringSoon(int days) {
        return getDaysUntilExpiration() <= days;
    }
}
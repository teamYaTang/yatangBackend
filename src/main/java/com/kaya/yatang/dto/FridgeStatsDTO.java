package com.kaya.yatang.dto;

import com.kaya.yatang.db.entity.FreezerItem;
import com.kaya.yatang.db.entity.Fridge;
import com.kaya.yatang.db.entity.FridgeItem;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FridgeStatsDTO {
    private Long fridgeId;
    private String fridgeName;
    private int totalItems;
    private int fridgeItems;
    private int freezerItems;
    private int expiredItems;
    private int expiringSoon3Days;
    private int expiringSoon7Days;
    private LocalDateTime lastUpdated;

    public FridgeStatsDTO(Fridge fridge) {
        this.fridgeId = fridge.getId();
        this.fridgeName = fridge.getName();
        this.fridgeItems = fridge.getFridgeItems().size();
        this.freezerItems = fridge.getFreezerItems().size();
        this.totalItems = this.fridgeItems + this.freezerItems;
        this.lastUpdated = fridge.getUpdatedAt();

        // 유통기한 통계 계산
        LocalDate today = LocalDate.now();

        // 냉장실 통계
        for (FridgeItem item : fridge.getFridgeItems()) {
            if (item.getExpirationDate() != null) {
                long daysUntil = ChronoUnit.DAYS.between(today, item.getExpirationDate());
                if (daysUntil < 0) {
                    this.expiredItems++;
                } else if (daysUntil <= 3) {
                    this.expiringSoon3Days++;
                } else if (daysUntil <= 7) {
                    this.expiringSoon7Days++;
                }
            }
        }

        // 냉동실 통계
        for (FreezerItem item : fridge.getFreezerItems()) {
            if (item.getExpirationDate() != null) {
                long daysUntil = ChronoUnit.DAYS.between(today, item.getExpirationDate());
                if (daysUntil < 0) {
                    this.expiredItems++;
                } else if (daysUntil <= 3) {
                    this.expiringSoon3Days++;
                } else if (daysUntil <= 7) {
                    this.expiringSoon7Days++;
                }
            }
        }
    }
}
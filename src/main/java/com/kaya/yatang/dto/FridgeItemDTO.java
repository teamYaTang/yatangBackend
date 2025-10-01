package com.kaya.yatang.dto;

import com.kaya.yatang.db.entity.FridgeItem;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FridgeItemDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private String unit;
    private LocalDate expirationDate;
    private LocalDate manufactureDate;
    private String memo;
    private Long fridgeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long daysUntilExpiration;
    private Boolean isExpired;
    private Boolean isExpiringSoon;

    // Entity -> DTO 변환 생성자
    public FridgeItemDTO(FridgeItem item) {
        this.id = item.getId();
        this.name = item.getName();
        this.quantity = item.getQuantity();
        this.unit = item.getUnit();
        this.expirationDate = item.getExpirationDate();
        this.manufactureDate = item.getManufactureDate();
        this.memo = item.getMemo();
        this.fridgeId = item.getFridge().getId();
        this.createdAt = item.getCreatedAt();
        this.updatedAt = item.getUpdatedAt();
        this.daysUntilExpiration = item.getDaysUntilExpiration();
        this.isExpired = item.isExpired();
        this.isExpiringSoon = item.isExpiringSoon(3);
    }
}
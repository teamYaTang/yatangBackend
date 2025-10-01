package com.kaya.yatang.dto;

import com.kaya.yatang.db.entity.FreezerItem;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FreezerItemDTO {
    private Long id;
    private String name;
    private Integer quantity;
    private String unit;
    private LocalDate expirationDate;
    private LocalDate manufactureDate;
    private LocalDate freezeDate;
    private String memo;
    private Long fridgeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long frozenDays;
    private Long daysUntilExpiration;
    private Boolean isExpired;
    private Boolean isLongTermFrozen;

    // Entity -> DTO 변환 생성자
    public FreezerItemDTO(FreezerItem item) {
        this.id = item.getId();
        this.name = item.getName();
        this.quantity = item.getQuantity();
        this.unit = item.getUnit();
        this.expirationDate = item.getExpirationDate();
        this.manufactureDate = item.getManufactureDate();
        this.freezeDate = item.getFreezeDate();
        this.memo = item.getMemo();
        this.fridgeId = item.getFridge().getId();
        this.createdAt = item.getCreatedAt();
        this.updatedAt = item.getUpdatedAt();
        this.frozenDays = item.getFrozenDays();
        this.daysUntilExpiration = item.getDaysUntilExpiration();
        this.isExpired = item.isExpired();
        this.isLongTermFrozen = item.isLongTermFrozen();
    }
}
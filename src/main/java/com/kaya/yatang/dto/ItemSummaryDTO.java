package com.kaya.yatang.dto;

import com.kaya.yatang.db.entity.FreezerItem;
import com.kaya.yatang.db.entity.FridgeItem;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSummaryDTO {
    private Long itemId;
    private String name;
    private String storageType; // "냉장실" or "냉동실"
    private LocalDate expirationDate;
    private long daysUntilExpiration;
    private int quantity;
    private String unit;

    // FridgeItem용 생성자
    public ItemSummaryDTO(FridgeItem item, String storageType) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.storageType = storageType;
        this.expirationDate = item.getExpirationDate();
        this.daysUntilExpiration = item.getDaysUntilExpiration();
        this.quantity = item.getQuantity();
        this.unit = item.getUnit();
    }

    // FreezerItem용 생성자
    public ItemSummaryDTO(FreezerItem item, String storageType) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.storageType = storageType;
        this.expirationDate = item.getExpirationDate();
        if (item.getExpirationDate() != null) {
            this.daysUntilExpiration = ChronoUnit.DAYS.between(LocalDate.now(), item.getExpirationDate());
        }
        this.quantity = item.getQuantity();
        this.unit = item.getUnit();
    }
}
package com.kaya.yatang.dto.request;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private String name;
    private Integer quantity;
    private String unit;
    private LocalDate expirationDate;
    private LocalDate manufactureDate;
    private LocalDate freezeDate; // FreezerItem 전용
    private String memo;
}
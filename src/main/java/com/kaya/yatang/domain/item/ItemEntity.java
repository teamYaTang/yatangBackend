package com.kaya.yatang.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaya.yatang.domain.fridge.FridgeEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne 여러개의 아이템이 하나의 냉장고에 존재
    @ManyToOne
    @JoinColumn(name = "fridgeid")
//    @JsonIgnore
    private FridgeEntity fridgeEntity;
}

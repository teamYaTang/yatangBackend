package com.kaya.yatang.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaya.yatang.domain.fridge.FridgeEntity;
import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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

    @Column
    private String itemname;

//  여러개의 아이템이 하나의 냉장고에 존재
    @ManyToOne
    @JoinColumn(name = "fridge_id")
    private FridgeEntity fridgeEntity;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDate.now();
    }
}

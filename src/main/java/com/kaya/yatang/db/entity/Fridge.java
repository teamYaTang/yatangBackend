package com.kaya.yatang.db.entity;

import com.kaya.yatang.domain.item.ItemEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Fridge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER)
    private List<ItemEntity> itemEntityList = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }

    public static Fridge createFridge(User userEntity){
        Fridge fridgeEntity = new Fridge();
        fridgeEntity.user = userEntity;

        return fridgeEntity;
    }
}

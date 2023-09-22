package com.kaya.yatang.domain.fridge;

import com.kaya.yatang.entity.ItemEntity;
import com.kaya.yatang.entity.UserEntity;
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
public class FridgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "userid")
    UserEntity userEntity;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ItemEntity> itemEntityList = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }

    public static FridgeEntity createFridge(UserEntity userEntity){
        FridgeEntity fridgeEntity = new FridgeEntity();
        fridgeEntity.userEntity = userEntity;

        return fridgeEntity;
    }
}

package com.kaya.yatang.domain.fridge;

import com.kaya.yatang.entity.ItemEntity;
import com.kaya.yatang.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

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
    private List<ItemEntity> items = new ArrayList<>();

}

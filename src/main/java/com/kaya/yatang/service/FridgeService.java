package com.kaya.yatang.service;

import com.kaya.yatang.domain.fridge.FridgeEntity;
import com.kaya.yatang.domain.fridge.FridgeRepository;
import com.kaya.yatang.domain.item.ItemEntity;
import com.kaya.yatang.entity.UserEntity;
import com.kaya.yatang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FridgeService {

    @Autowired
    private FridgeRepository fridgeRepository;
    @Autowired
    private UserRepository userRepository;

    public void createFridge(UserEntity userEntity){
        FridgeEntity fridgeEntity = FridgeEntity.createFridge(userEntity);
        fridgeRepository.save(fridgeEntity);
    }

    // 장바구니 생성
    @Transactional
    public void addFridge(UserEntity userEntity, ItemEntity itemEntity){
        FridgeEntity fridgeEntity = fridgeRepository.findByUserid(userEntity.getId());

        // Fridge가 비어있으면 생성
        if (fridgeEntity == null) {
            fridgeEntity = FridgeEntity.createFridge(userEntity);
            fridgeRepository.save(fridgeEntity);
        }

    }

}

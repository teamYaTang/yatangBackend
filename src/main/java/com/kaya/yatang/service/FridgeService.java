package com.kaya.yatang.service;

import com.kaya.yatang.db.entity.Fridge;
import com.kaya.yatang.db.repository.FridgeRepository;
import com.kaya.yatang.domain.item.ItemEntity;
import com.kaya.yatang.db.entity.User;
import com.kaya.yatang.db.repository.UserRepository;
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

    public void createFridge(User userEntity){
        Fridge fridgeEntity = Fridge.createFridge(userEntity);
        fridgeRepository.save(fridgeEntity);
    }

    // 장바구니 생성
    @Transactional
    public void addFridge(User userEntity, ItemEntity itemEntity){
        Fridge fridgeEntity = fridgeRepository.findByUserId(userEntity.getId());

        // Fridge가 비어있으면 생성
        if (fridgeEntity == null) {
            fridgeEntity = Fridge.createFridge(userEntity);
            fridgeRepository.save(fridgeEntity);
        }

    }

}

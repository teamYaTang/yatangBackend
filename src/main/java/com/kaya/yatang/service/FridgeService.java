package com.kaya.yatang.service;

import com.kaya.yatang.domain.fridge.FridgeEntity;
import com.kaya.yatang.domain.fridge.FridgeRepository;
import com.kaya.yatang.entity.UserEntity;
import com.kaya.yatang.repository.ItemRepository;
import com.kaya.yatang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public void createFridge(UserEntity userEntity){
        FridgeEntity fridgeEntity = FridgeEntity.createFridge(userEntity);
        fridgeRepository.save(fridgeEntity);
    }
}

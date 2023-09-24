package com.kaya.yatang.service;

import com.kaya.yatang.domain.fridge.FridgeEntity;
import com.kaya.yatang.domain.fridge.FridgeRepository;
import com.kaya.yatang.domain.item.ItemEntity;
import com.kaya.yatang.domain.item.ItemRepository;
import com.kaya.yatang.entity.UserEntity;
import com.kaya.yatang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemService {

    @Autowired
    private FridgeRepository fridgeRepository;
    @Autowired
    private ItemRepository itemRepository;

    public ItemEntity save(String userid, ItemEntity itemEntity) {
        FridgeEntity fridgeEntity = fridgeRepository.findByUserid(userid);
        itemEntity.setFridgeEntity(fridgeEntity);
        return itemRepository.save(itemEntity);
    }
}

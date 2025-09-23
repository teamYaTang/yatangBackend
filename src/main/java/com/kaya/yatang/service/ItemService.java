package com.kaya.yatang.service;

import com.kaya.yatang.db.entity.Fridge;
import com.kaya.yatang.db.repository.FridgeRepository;
import com.kaya.yatang.domain.item.ItemEntity;
import com.kaya.yatang.domain.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemService {

    @Autowired
    private FridgeRepository fridgeRepository;
    @Autowired
    private ItemRepository itemRepository;

    public ItemEntity save(Long userId, ItemEntity itemEntity) {
        Fridge fridgeEntity = fridgeRepository.findByUserId(userId);
        itemEntity.setFridgeEntity(fridgeEntity);
        return itemRepository.save(itemEntity);
    }
}

package com.kaya.yatang.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
//    ItemEntity findByFridgeid(Long fridgeid);
    List<ItemEntity> findByItemname(String itemname);
}

package com.kaya.yatang.repository;

import com.kaya.yatang.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByItemname(String itemname);
    List<ItemEntity> findByUserid(String userid);
}
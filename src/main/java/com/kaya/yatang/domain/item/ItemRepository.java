package com.kaya.yatang.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    ItemEntity findByFridgeid(Long fridgeid);
}

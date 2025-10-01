package com.kaya.yatang.service;

import com.kaya.yatang.db.entity.Fridge;
import com.kaya.yatang.db.entity.FreezerItem;
import com.kaya.yatang.dto.FreezerItemDTO;
import com.kaya.yatang.dto.request.ItemRequest;
import com.kaya.yatang.db.repository.FreezerItemRepository;
import com.kaya.yatang.db.repository.FridgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FreezerItemService {

    private final FreezerItemRepository freezerItemRepository;
    private final FridgeRepository fridgeRepository;

    /**
     * 냉동실 아이템 추가
     */
    public FreezerItemDTO createItem(Long fridgeId, Long userId, ItemRequest request) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        FreezerItem item = FreezerItem.builder()
            .fridge(fridge)
            .name(request.getName())
            .quantity(request.getQuantity())
            .unit(request.getUnit())
            .expirationDate(request.getExpirationDate())
            .manufactureDate(request.getManufactureDate())
            .freezeDate(request.getFreezeDate() != null ? request.getFreezeDate() : LocalDate.now())
            .memo(request.getMemo())
            .build();

        FreezerItem savedItem = freezerItemRepository.save(item);
        return new FreezerItemDTO(savedItem);
    }

    /**
     * 냉동실 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    public List<FreezerItemDTO> getItemsByFridge(Long fridgeId, Long userId) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        return freezerItemRepository.findByFridgeId(fridgeId).stream()
            .map(FreezerItemDTO::new)
            .collect(Collectors.toList());
    }

    /**
     * 냉동실 아이템 상세 조회
     */
    @Transactional(readOnly = true)
    public FreezerItemDTO getItemById(Long itemId, Long userId) {
        FreezerItem item = freezerItemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

        // 소유자 확인
        if (!item.getFridge().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 아이템에 접근할 권한이 없습니다.");
        }

        return new FreezerItemDTO(item);
    }

    /**
     * 냉동실 아이템 수정
     */
    public FreezerItemDTO updateItem(Long itemId, Long userId, ItemRequest request) {
        FreezerItem item = freezerItemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

        // 소유자 확인
        if (!item.getFridge().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 아이템을 수정할 권한이 없습니다.");
        }

        // 수정
        if (request.getName() != null) item.setName(request.getName());
        if (request.getQuantity() != null) item.setQuantity(request.getQuantity());
        if (request.getUnit() != null) item.setUnit(request.getUnit());
        if (request.getExpirationDate() != null) item.setExpirationDate(request.getExpirationDate());
        if (request.getManufactureDate() != null) item.setManufactureDate(request.getManufactureDate());
        if (request.getFreezeDate() != null) item.setFreezeDate(request.getFreezeDate());
        if (request.getMemo() != null) item.setMemo(request.getMemo());

        FreezerItem updatedItem = freezerItemRepository.save(item);
        return new FreezerItemDTO(updatedItem);
    }

    /**
     * 냉동실 아이템 삭제
     */
    public void deleteItem(Long itemId, Long userId) {
        FreezerItem item = freezerItemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

        // 소유자 확인
        if (!item.getFridge().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 아이템을 삭제할 권한이 없습니다.");
        }

        freezerItemRepository.delete(item);
    }

    /**
     * 냉동실 아이템 검색
     */
    @Transactional(readOnly = true)
    public List<FreezerItemDTO> searchItems(Long fridgeId, Long userId, String keyword) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        return freezerItemRepository.findByFridgeIdAndNameContaining(fridgeId, keyword).stream()
            .map(FreezerItemDTO::new)
            .collect(Collectors.toList());
    }

    /**
     * 장기 보관 아이템 조회 (6개월 이상)
     */
    @Transactional(readOnly = true)
    public List<FreezerItemDTO> getLongTermFrozenItems(Long fridgeId, Long userId) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        return freezerItemRepository.findLongTermFrozenItems(fridgeId, sixMonthsAgo).stream()
            .map(FreezerItemDTO::new)
            .collect(Collectors.toList());
    }

    /**
     * 유통기한 임박 아이템 조회
     */
    @Transactional(readOnly = true)
    public List<FreezerItemDTO> getExpiringSoonItems(Long fridgeId, Long userId, int days) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);

        return freezerItemRepository.findExpiringSoonItems(fridgeId, today, endDate).stream()
            .map(FreezerItemDTO::new)
            .collect(Collectors.toList());
    }

    /**
     * 권한 확인 및 냉장고 조회 (헬퍼 메서드)
     */
    private Fridge getFridgeWithAuth(Long fridgeId, Long userId) {
        Fridge fridge = fridgeRepository.findById(fridgeId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 냉장고입니다."));

        if (!fridge.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 냉장고에 접근할 권한이 없습니다.");
        }

        return fridge;
    }
}
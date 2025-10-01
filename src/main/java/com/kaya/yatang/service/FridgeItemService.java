package com.kaya.yatang.service;

import com.kaya.yatang.db.entity.Fridge;
import com.kaya.yatang.db.entity.FridgeItem;
import com.kaya.yatang.dto.FridgeItemDTO;
import com.kaya.yatang.dto.request.ItemRequest;
import com.kaya.yatang.db.repository.FridgeItemRepository;
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
public class FridgeItemService {

    private final FridgeItemRepository fridgeItemRepository;
    private final FridgeRepository fridgeRepository;

    /**
     * 냉장실 아이템 추가
     */
    public FridgeItemDTO createItem(Long fridgeId, Long userId, ItemRequest request) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        FridgeItem item = FridgeItem.builder()
            .fridge(fridge)
            .name(request.getName())
            .quantity(request.getQuantity())
            .unit(request.getUnit())
            .expirationDate(request.getExpirationDate())
            .manufactureDate(request.getManufactureDate())
            .memo(request.getMemo())
            .build();

        FridgeItem savedItem = fridgeItemRepository.save(item);
        return new FridgeItemDTO(savedItem);
    }

    /**
     * 냉장실 아이템 목록 조회
     */
    @Transactional(readOnly = true)
    public List<FridgeItemDTO> getItemsByFridge(Long fridgeId, Long userId) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        return fridgeItemRepository.findByFridgeId(fridgeId).stream()
            .map(FridgeItemDTO::new)
            .collect(Collectors.toList());
    }

    /**
     * 냉장실 아이템 상세 조회
     */
    @Transactional(readOnly = true)
    public FridgeItemDTO getItemById(Long itemId, Long userId) {
        FridgeItem item = fridgeItemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

        // 소유자 확인
        if (!item.getFridge().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 아이템에 접근할 권한이 없습니다.");
        }

        return new FridgeItemDTO(item);
    }

    /**
     * 냉장실 아이템 수정
     */
    public FridgeItemDTO updateItem(Long itemId, Long userId, ItemRequest request) {
        FridgeItem item = fridgeItemRepository.findById(itemId)
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
        if (request.getMemo() != null) item.setMemo(request.getMemo());

        FridgeItem updatedItem = fridgeItemRepository.save(item);
        return new FridgeItemDTO(updatedItem);
    }

    /**
     * 냉장실 아이템 삭제
     */
    public void deleteItem(Long itemId, Long userId) {
        FridgeItem item = fridgeItemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));

        // 소유자 확인
        if (!item.getFridge().getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 아이템을 삭제할 권한이 없습니다.");
        }

        fridgeItemRepository.delete(item);
    }

    /**
     * 냉장실 아이템 검색
     */
    @Transactional(readOnly = true)
    public List<FridgeItemDTO> searchItems(Long fridgeId, Long userId, String keyword) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        return fridgeItemRepository.findByFridgeIdAndNameContaining(fridgeId, keyword).stream()
            .map(FridgeItemDTO::new)
            .collect(Collectors.toList());
    }

    /**
     * 유통기한 임박 아이템 조회
     */
    @Transactional(readOnly = true)
    public List<FridgeItemDTO> getExpiringSoonItems(Long fridgeId, Long userId, int days) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);

        return fridgeItemRepository.findExpiringSoonItems(fridgeId, today, endDate).stream()
            .map(FridgeItemDTO::new)
            .collect(Collectors.toList());
    }

    /**
     * 만료된 아이템 조회
     */
    @Transactional(readOnly = true)
    public List<FridgeItemDTO> getExpiredItems(Long fridgeId, Long userId) {
        Fridge fridge = getFridgeWithAuth(fridgeId, userId);

        return fridgeItemRepository.findExpiredItems(fridgeId, LocalDate.now()).stream()
            .map(FridgeItemDTO::new)
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
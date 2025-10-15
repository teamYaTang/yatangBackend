package com.kaya.yatang.component.initializer;

import com.kaya.yatang.code.LoginType;
import com.kaya.yatang.config.DefaultDataConfig;
import com.kaya.yatang.db.entity.*;
import com.kaya.yatang.db.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final DefaultDataConfig defaultDataConfig;
    private final UserRepository userRepository;
    private final FridgeRepository fridgeRepository;
    private final FridgeItemRepository fridgeItemRepository;
    private final FreezerItemRepository freezerItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        // create 설정이 true일 때만 초기 데이터 생성
        if (!Boolean.TRUE.equals(defaultDataConfig.getCreate())) {
            log.info("초기 데이터 생성이 비활성화되어 있습니다.");
            return;
        }

        // 이미 데이터가 있으면 건너뛰기
        if (userRepository.count() > 0) {
            log.info("이미 데이터가 존재합니다. 초기화를 건너뜁니다.");
            return;
        }

        log.info("초기 데이터 생성을 시작합니다...");

        // 1. 사용자 생성
        Map<Long, User> userMap = createUsers();

        // 2. 냉장고 생성
        Map<Long, Fridge> fridgeMap = createFridges(userMap);

        // 3. 냉장실 아이템 생성
        createFridgeItems(fridgeMap);

        // 4. 냉동실 아이템 생성
        createFreezerItems(fridgeMap);

        log.info("초기 데이터 생성이 완료되었습니다.");
    }

    private Map<Long, User> createUsers() {
        Map<Long, User> userMap = new HashMap<>();

        if (defaultDataConfig.getUsers() != null) {
            for (int i = 0; i < defaultDataConfig.getUsers().size(); i++) {
                DefaultDataConfig.UserData userData = defaultDataConfig.getUsers().get(i);

                // 중복 체크 (username과 email 모두 체크)
                if (userRepository.existsByUsername(userData.getUsername())) {
                    log.warn("사용자 '{}' 는 이미 존재합니다. 건너뜁니다.", userData.getUsername());
                    continue;
                }

                if (userRepository.existsByEmail(userData.getEmail())) {
                    log.warn("이메일 '{}' 는 이미 사용 중입니다. 사용자 '{}' 생성을 건너뜁니다.",
                        userData.getEmail(), userData.getUsername());
                    continue;
                }

                // 임시 닉네임 생성
                String tempNickname = "user_" + userData.getUsername();

                User user = User.builder()
                    .username(userData.getUsername())
                    .password(passwordEncoder.encode(userData.getPassword()))
                    .email(userData.getEmail())
                    .nickname(tempNickname)
                    .loginType(LoginType.NORMAL)
                    .build();

                User savedUser = userRepository.save(user);
                userMap.put((long) (i + 1), savedUser);

                log.info("사용자 생성: {} (ID: {})", savedUser.getUsername(), savedUser.getId());
            }
        }

        return userMap;
    }

    private Map<Long, Fridge> createFridges(Map<Long, User> userMap) {
        Map<Long, Fridge> fridgeMap = new HashMap<>();

        if (defaultDataConfig.getFridge() != null) {
            for (int i = 0; i < defaultDataConfig.getFridge().size(); i++) {
                DefaultDataConfig.FridgeData fridgeData = defaultDataConfig.getFridge().get(i);

                User user = userMap.get(fridgeData.getUserId());
                if (user == null) {
                    log.warn("사용자 ID {} 를 찾을 수 없습니다. 냉장고 '{}' 생성을 건너뜁니다.",
                        fridgeData.getUserId(), fridgeData.getName());
                    continue;
                }

                Fridge fridge = Fridge.builder()
                    .name(fridgeData.getName())
                    .description(fridgeData.getDescription())
                    .user(user)
                    .build();

                Fridge savedFridge = fridgeRepository.save(fridge);
                fridgeMap.put((long) (i + 1), savedFridge);

                log.info("냉장고 생성: {} (ID: {}, 소유자: {})",
                    savedFridge.getName(), savedFridge.getId(), user.getUsername());
            }
        }

        return fridgeMap;
    }

    private void createFridgeItems(Map<Long, Fridge> fridgeMap) {
        if (defaultDataConfig.getFridgeItems() != null) {
            for (DefaultDataConfig.FridgeItemData itemData : defaultDataConfig.getFridgeItems()) {
                Fridge fridge = fridgeMap.get(itemData.getFridgeId());
                if (fridge == null) {
                    log.warn("냉장고 ID {} 를 찾을 수 없습니다. 아이템 '{}' 생성을 건너뜁니다.",
                        itemData.getFridgeId(), itemData.getItemName());
                    continue;
                }

                FridgeItem item = FridgeItem.builder()
                    .fridge(fridge)
                    .name(itemData.getItemName())
                    .quantity(itemData.getQuantity())
                    .unit(itemData.getUnit() != null ? itemData.getUnit() : "개")
                    .expirationDate(itemData.getExpirationDate())
                    .manufactureDate(itemData.getManufactureDate())
                    .memo(itemData.getMemo())
                    .build();

                FridgeItem savedItem = fridgeItemRepository.save(item);

                log.info("냉장실 아이템 생성: {} (수량: {}{}, 냉장고: {})",
                    savedItem.getName(), savedItem.getQuantity(), savedItem.getUnit(),
                    fridge.getName());
            }
        }
    }

    private void createFreezerItems(Map<Long, Fridge> fridgeMap) {
        if (defaultDataConfig.getFreezerItems() != null) {
            for (DefaultDataConfig.FreezerItemData itemData : defaultDataConfig.getFreezerItems()) {
                Fridge fridge = fridgeMap.get(itemData.getFridgeId());
                if (fridge == null) {
                    log.warn("냉장고 ID {} 를 찾을 수 없습니다. 아이템 '{}' 생성을 건너뜁니다.",
                        itemData.getFridgeId(), itemData.getItemName());
                    continue;
                }

                FreezerItem item = FreezerItem.builder()
                    .fridge(fridge)
                    .name(itemData.getItemName())
                    .quantity(itemData.getQuantity())
                    .unit(itemData.getUnit() != null ? itemData.getUnit() : "개")
                    .expirationDate(itemData.getExpirationDate())
                    .manufactureDate(itemData.getManufactureDate())
                    .freezeDate(itemData.getFreezeDate())
                    .memo(itemData.getMemo())
                    .build();

                FreezerItem savedItem = freezerItemRepository.save(item);

                log.info("냉동실 아이템 생성: {} (수량: {}{}, 냉장고: {})",
                    savedItem.getName(), savedItem.getQuantity(), savedItem.getUnit(),
                    fridge.getName());
            }
        }
    }
}
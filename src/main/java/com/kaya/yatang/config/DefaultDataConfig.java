package com.kaya.yatang.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "default")
@Getter
@Setter
public class DefaultDataConfig {

    private Boolean create;
    private List<UserData> users;
    private List<FridgeData> fridge;
    private List<FridgeItemData> fridgeItems;
    private List<FreezerItemData> freezerItems;

    @Getter
    @Setter
    public static class UserData {
        private String username;
        private String password;
        private String email;
    }

    @Getter
    @Setter
    public static class FridgeData {
        private String name;
        private Long userId;
        private String description;
    }

    @Getter
    @Setter
    public static class FridgeItemData {
        private Long fridgeId;
        private String itemName;
        private Integer quantity;
        private String unit;
        private LocalDate expirationDate;
        private LocalDate manufactureDate;
        private String memo;
    }

    @Getter
    @Setter
    public static class FreezerItemData {
        private Long fridgeId;
        private String itemName;
        private Integer quantity;
        private String unit;
        private LocalDate expirationDate;
        private LocalDate manufactureDate;
        private LocalDate freezeDate;
        private String memo;
    }
}
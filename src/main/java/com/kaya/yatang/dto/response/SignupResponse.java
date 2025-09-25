package com.kaya.yatang.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse {
    private Long userId;
    private String email;
    private String username;
    private String nickname;
    private Long mainFridgeId; // 메인 냉장고 ID
    private String message;
    private LocalDateTime createdAt;
}
package com.kaya.yatang.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NicknameUpdateRequest {
//    @NotBlank(message = "닉네임은 필수입니다.")
//    @Size(min = 2, max = 15, message = "닉네임은 2자 이상 15자 이하여야 합니다.")
//    @Pattern(regexp = "^[가-힣a-zA-Z0-9_]+$",
//        message = "닉네임은 한글, 영문, 숫자, 언더스코어만 사용 가능합니다.")
    private String nickname;
}
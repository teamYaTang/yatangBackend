package com.kaya.yatang.dto;

import com.kaya.yatang.db.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private long id;
    private String username;
    private String email;
    private String password;
    private String LoginType; // NORMAL, KAKAO, GOOGLE, NAVER
    private String socialId; // 소셜 로그인용 ID
    private String nickname; // 닉네임

    public static UserDTO toUserDTO(User userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setLoginType(userEntity.getLoginType().name());
        userDTO.setSocialId(userEntity.getSocialId());
        userDTO.setNickname(userEntity.getNickname());
        return userDTO;
    }
}

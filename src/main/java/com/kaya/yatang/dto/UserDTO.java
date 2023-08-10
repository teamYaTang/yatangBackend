package com.kaya.yatang.dto;

import com.kaya.yatang.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private long id;
    private String username;
    private String userid;
    private String userpw;
    private String nickname;

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDTO.getId());
        userDTO.setUsername(userDTO.getUsername());
        userDTO.setUserid(userDTO.getUserid());
        userDTO.setUserpw(userDTO.getUserpw());
        userDTO.setNickname(userDTO.getNickname());
        return userDTO;
    }
}

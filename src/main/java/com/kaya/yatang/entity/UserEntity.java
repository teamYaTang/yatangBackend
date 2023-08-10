package com.kaya.yatang.entity;

import com.kaya.yatang.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column(unique = true)
    private String userid;

    @Column
    private String userpw;

    @Column
    private String nickname;

    // 회원가입
    public static UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setUserid(userDTO.getUserid());
        userEntity.setUserpw(userDTO.getUserpw());
        return userEntity;
    }

    // 닉네임 설정
    public static UserEntity toUpdateUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
//        userEntity.setUsername(userDTO.getUsername());
//        userEntity.setUserid(userDTO.getUserid());
//        userEntity.setUserpw(userDTO.getUserpw());
        userEntity.setNickname(userDTO.getNickname());
        return userEntity;
    }

}

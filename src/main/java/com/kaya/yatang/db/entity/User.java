package com.kaya.yatang.db.entity;

import com.kaya.yatang.code.Role;
import com.kaya.yatang.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//import org.springframework.context.annotation.Role;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Fridge fridgeEntity;

    // 회원가입
    public static User toUserEntity(UserDTO userDTO) {
        User userEntity = new User();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setUserid(userDTO.getUserid());
        userEntity.setUserpw(userDTO.getUserpw());
        userEntity.setNickname(userDTO.getNickname());
        return userEntity;
    }

    // 닉네임 설정
    public static User toUpdateUserEntity(UserDTO userDTO) {
        User userEntity = new User();
        userEntity.setId(userDTO.getId());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setUserid(userDTO.getUserid());
        userEntity.setUserpw(userDTO.getUserpw());
        userEntity.setNickname(userDTO.getNickname());
        return userEntity;
    }
}

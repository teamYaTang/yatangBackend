package com.kaya.yatang.security;

import com.kaya.yatang.entity.UserEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private UserEntity userEntity;

    public SecurityUser(UserEntity userEntity) {
        super(userEntity.getUserid().toString(), userEntity.getUserpw(),
                AuthorityUtils.createAuthorityList(userEntity.getRole().toString()));
        this.userEntity = userEntity;
    }

    public UserEntity userEntity() {
        return userEntity;
    }
}
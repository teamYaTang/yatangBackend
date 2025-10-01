package com.kaya.yatang.security;

import com.kaya.yatang.db.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class SecurityUser extends org.springframework.security.core.userdetails.User {
    private User userEntity;

    public SecurityUser(User userEntity) {
        super(userEntity.getId().toString(), userEntity.getPassword(),
                AuthorityUtils.createAuthorityList(userEntity.getUsername()));
//                AuthorityUtils.createAuthorityList(userEntity.getRole().toString()));
        this.userEntity = userEntity;
    }

    public User userEntity() {
        return userEntity;
    }
}
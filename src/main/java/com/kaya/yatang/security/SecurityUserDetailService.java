package com.kaya.yatang.security;

import com.kaya.yatang.entity.UserEntity;
import com.kaya.yatang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        Optional<UserEntity> optional = userRepository.findByUserid(userid);
        if(!optional.isPresent()) {
            throw new UsernameNotFoundException(userid + " 사용자 없음");
        } else {
            UserEntity userEntity = optional.get();
            return new SecurityUser(userEntity);
        }
    }
}
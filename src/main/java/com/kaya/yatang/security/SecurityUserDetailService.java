package com.kaya.yatang.security;

import com.kaya.yatang.db.entity.User;
import com.kaya.yatang.db.repository.UserRepository;
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
        Optional<User> optional = userRepository.findByUserid(userid);
        if(!optional.isPresent()) {
            throw new UsernameNotFoundException(userid + " 사용자 없음");
        } else {
            User userEntity = optional.get();
            return new SecurityUser(userEntity);
        }
    }
}
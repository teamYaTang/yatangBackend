package com.kaya.yatang.service;

import com.kaya.yatang.dto.UserDTO;
import com.kaya.yatang.entity.UserEntity;
import com.kaya.yatang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(UserDTO userDTO) {
        userDTO.setUserpw(passwordEncoder.encode(userDTO.getUserpw()));
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
    }

    public UserDTO login(UserDTO userDTO) {
        Optional<UserEntity> byUserid = userRepository.findByUserid(userDTO.getUserid());

        if (byUserid.isPresent()) {
            UserEntity userEntity = byUserid.get();

            if (passwordEncoder.matches(userDTO.getUserpw(), userEntity.getUserpw())) {
//            if (userEntity.getUserpw().equals(userDTO.getUserpw())) {     // 암호화 X
                // 비밀번호 일치
                UserDTO dto = UserDTO.toUserDTO(userEntity);
                return dto;
            } else {
                // 비밀번호 불일치
                return null;
            }
        } else {
            return null;
        }
    }

    public UserDTO findById(Long id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    public UserDTO nicknameForm(String myUserid) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserid(myUserid);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    public void nickname(UserDTO userDTO) {
        userRepository.save(UserEntity.toUpdateUserEntity(userDTO));
    }

    public String useridCheck(String userid) {
        Optional<UserEntity> byUserid = userRepository.findByUserid(userid);
        if (byUserid.isPresent()) {
            return null;
        } else {
            return "ok";
        }
    }
}

package com.kaya.yatang.service;

import com.kaya.yatang.dto.UserDTO;
import com.kaya.yatang.db.entity.User;
import com.kaya.yatang.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(UserDTO userDTO) {
        userDTO.setUserpw(passwordEncoder.encode(userDTO.getUserpw()));
        User userEntity = User.toUserEntity(userDTO);
        userRepository.save(userEntity);
    }

    public UserDTO login(UserDTO userDTO) {
        Optional<User> byUserid = userRepository.findByUserid(userDTO.getUserid());

        if (byUserid.isPresent()) {
            User userEntity = byUserid.get();

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
        Optional<User> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    public UserDTO nicknameForm(String myUserid) {
        Optional<User> optionalUserEntity = userRepository.findByUserid(myUserid);
        if (optionalUserEntity.isPresent()) {
            return UserDTO.toUserDTO(optionalUserEntity.get());
        } else {
            return null;
        }
    }

    public void nickname(UserDTO userDTO) {
        userRepository.save(User.toUpdateUserEntity(userDTO));
    }

    public String useridCheck(String userid) {
        Optional<User> byUserid = userRepository.findByUserid(userid);
        if (byUserid.isPresent()) {
            return null;
        } else {
            return "ok";
        }
    }
}

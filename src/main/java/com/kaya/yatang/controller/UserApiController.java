package com.kaya.yatang.controller;

import com.kaya.yatang.domain.Role;
import com.kaya.yatang.dto.UserDTO;
import com.kaya.yatang.entity.UserEntity;
import com.kaya.yatang.repository.UserRepository;
import com.kaya.yatang.security.JwtTokenProvider;
import com.kaya.yatang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserApiController {

//    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    // 회원가입
//    @PostMapping("/register")
//    public UUID register(@RequestBody Map<String, String> user) {
//        return memberRepository.save(Member.builder()
//                .email(user.get("email"))
//                .password(passwordEncoder.encode(user.get("password")))
//                .nickname(user.get("nickname"))
//                .phone(user.get("phone"))
//                .role(Role.ROLE_MEMBER)
//                .build()).getId();
//    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {

        UserEntity userEntity = userRepository.findByUserid(user.get("userid"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디입니다."));

//        암호화 되지 않은 비밀번호
//        if (!userEntity.getUserpw().equals(user.get("userpw"))) {
//            throw new IllegalArgumentException("아이디 또는 비밀번호가 맞지 않습니다.");
//        }

//        암호화 된 비밀번호
        if (!passwordEncoder.matches(user.get("userpw"), userEntity.getUserpw())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 맞지 않습니다.");
        }

        return jwtTokenProvider.createToken(userEntity.getUserid(), userEntity.getRole());
    }
}

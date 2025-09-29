package com.kaya.yatang.controller;

import com.kaya.yatang.db.entity.User;
import com.kaya.yatang.db.repository.UserRepository;
import com.kaya.yatang.security.JwtTokenProvider;
import com.kaya.yatang.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
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

        System.out.println(user);

        User userEntity = userRepository.findByUserid(user.get("id"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디입니다."));

        System.out.println(user.get("id"));

//        암호화 되지 않은 비밀번호
//        if (!userEntity.getUserpw().equals(user.get("userpw"))) {
//            throw new IllegalArgumentException("아이디 또는 비밀번호가 맞지 않습니다.");
//        }

//        암호화 된 비밀번호
        if (!passwordEncoder.matches(user.get("password"), userEntity.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 맞지 않습니다.");
        } else {
            System.out.println(user.get("id"));
            System.out.println(user.get("password"));
        }

        return jwtTokenProvider.createToken(userEntity.getId(), userEntity.getUsername());
    }
}

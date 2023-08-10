package com.kaya.yatang.controller;

import com.kaya.yatang.dto.UserDTO;
import com.kaya.yatang.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입 페이지
    @GetMapping("/account/register")
    public String registerForm() { return "register"; }

    @PostMapping("/account/register")
    public String register(@ModelAttribute UserDTO userDTO) {
        userService.save(userDTO);
//        UserDTO registerResult = userService.save(userDTO);

        return "nickname";
    }

    // 닉네임 설정
    @GetMapping("/account/nickname")
    public String nicknameForm(HttpSession session, Model model) {
        String myUserid = (String) session.getAttribute("loginId");
//        String myName = (String) session.getAttribute("loginName");
        UserDTO userDTO = userService.nicknameForm(myUserid);
        model.addAttribute("nickname", userDTO);
        return "nickname";
    }

    @PostMapping("/account/nickname")
    public String nickname(@ModelAttribute UserDTO userDTO) {
        userService.nickname(userDTO);
        return "redirect:/user/" + userDTO.getId();
    }

    // 로그인 페이지
    @GetMapping("/account/login")
    public String loginForm() { return "login"; }

    @PostMapping("/account/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO loginResult = userService.login(userDTO);
        if (loginResult != null) {
            // 성공!
            session.setAttribute("loginId", loginResult.getUserid());
//            session.setAttribute("loginName", loginResult.getUsername());
            return "main";
        } else {
            // 실패!
            return "home";
        }
    }

    // 로그아웃
    @GetMapping("/account/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "home";
    }

    // 아이디 체크
    @PostMapping("/account/userid-check")
    public @ResponseBody String useridCheck(@RequestParam("userid") String userid) {
        System.out.println("userid = " + userid);
        String checkResult = userService.useridCheck(userid);
        return checkResult;
    }
}

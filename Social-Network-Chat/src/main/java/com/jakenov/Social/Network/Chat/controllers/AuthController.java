package com.jakenov.Social.Network.Chat.controllers;

import com.jakenov.Social.Network.Chat.entities.Permission;
import com.jakenov.Social.Network.Chat.entities.User;
import com.jakenov.Social.Network.Chat.jwt.JwtTokenProvider;
import com.jakenov.Social.Network.Chat.repositories.UserRepository;
import com.jakenov.Social.Network.Chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }
    @GetMapping("/verify-code")
    public String showVerifyCode(){
        return "verify-code";
    }



    @PostMapping("/register")
    public String register(@RequestParam("nickname") String nickname,
                           @RequestParam("full-name")String fullName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("re-password") String rePassword){
        return userService.registerUser(nickname,fullName,email,password,rePassword);
    }
    @PostMapping("/verify-code")
    public String checkVerifyCode(@RequestParam("code") short code, Model model) {
        return userService.checkCode(code,model);
    }
}

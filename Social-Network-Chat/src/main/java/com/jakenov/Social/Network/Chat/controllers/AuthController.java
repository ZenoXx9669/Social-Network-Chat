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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

//    @PostMapping("/login")
//    public String authenticateUser(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes,Model model) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(email, password)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
//            String jwt = tokenProvider.generateToken(user);
//
//            // Логирование
//            System.out.println("Authenticated user: " + user.getEmail());
//            System.out.println("Security Context: " + SecurityContextHolder.getContext().getAuthentication());
//            model.addAttribute("jwt",jwt);
//            return "redirect:/chat"; // перенаправление на страницу чата
//        } catch (Exception e) {
//            redirectAttributes.addFlashAtt  ribute("error", "Invalid email or password!");
//            return "redirect:/login"; // если аутентификация не удалась, перенаправляем обратно на страницу входа
//        }
//    }


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
        User checkUser1 = userRepository.findByNickName(nickname).orElse(null);
        User checkUser2 = userRepository.findByEmail(email).orElse(null);
        String redirect = "register?nicknameExist";
        if (checkUser1 == null){
            redirect = "register?emailExist";
            if (checkUser2 == null){
                redirect = "register?passwordsNotMatch";
                if (password.equals(rePassword)){
                    User user = new User();
                    user.setNickName(nickname);
                    user.setFullName(fullName);
                    user.setEmail(email);
                    user.setPassword(password);
                    Permission permission = new Permission();
                    permission.setId(1L);
                    List<Permission> permissions = new ArrayList<>();
                    permissions.add(permission);
                    user.setPermissions(permissions);
                    userService.registerUser(user);
                    redirect = "verify-code";
                }
            }
        }

        return "redirect:" + redirect;
    }
    @PostMapping("/verify-code")
    public String checkVerifyCode(@RequestParam("code") short code, Model model) {
        try {
            userService.checkCode(code);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "verify-code";
        }
        return "redirect:/login";
    }
}

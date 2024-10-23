package com.jakenov.Social.Network.Chat.services;

import com.jakenov.Social.Network.Chat.code.CodeForConfirm;
import com.jakenov.Social.Network.Chat.entities.Permission;
import com.jakenov.Social.Network.Chat.entities.User;
import com.jakenov.Social.Network.Chat.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User globalUser;

    public String registerUser(String nickname, String fullName, String email, String password, String rePassword) {
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
                    user.setPassword(passwordEncoder.encode(password));
                    Permission permission = new Permission();
                    permission.setId(1L);
                    List<Permission> permissions = new ArrayList<>();
                    permissions.add(permission);
                    user.setPermissions(permissions);
                    userRepository.save(user);
                    redirect = "verify-code";
                    CodeForConfirm confirm = new CodeForConfirm();
                    String code = String.valueOf(confirm.getCode());
                    user.setCode(code);
                    globalUser = user;
                    sendVerificationEmail(user.getEmail(), code);
                }
            }
        }

        return "redirect:" + redirect;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("username not found");
        }
        return user.get();
    }

    public String checkCode(short code, Model model) {
        User user = globalUser;
        try {
            if (compareToCode(user.getCode(), code)) {
                userRepository.save(user);
            }else {
                throw new IllegalArgumentException("Неверный код подтверждения.");
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "verify-code";
        }
        return "redirect:/login";
    }


    private boolean compareToCode(String code, short equalsCode) {
        return code.equals(String.valueOf(equalsCode));
    }

    private void sendVerificationEmail(String to, String code) {
        String subject = "Ваш код подтверждения: " + code;
        String text = "Спасибо, что выбрали нашу компанию";

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

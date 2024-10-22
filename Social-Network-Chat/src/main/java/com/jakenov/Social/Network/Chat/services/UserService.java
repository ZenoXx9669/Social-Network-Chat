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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User globalUser;

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRePassword(null);

        Permission permission = new Permission();
        permission.setId(1L);

        user.getPermissions().add(permission);

        CodeForConfirm confirm = new CodeForConfirm();
        String code = String.valueOf(confirm.getCode());
        user.setCode(code);
        globalUser = user;
        sendVerificationEmail(user.getEmail(), code);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("username not found");
        }
        return user.get();
    }

    public void checkCode(short code) {
        User user = globalUser;
        if (compareToCode(user.getCode(), code)) {
            userRepository.save(user);
        }else {
            throw new IllegalArgumentException("Неверный код подтверждения.");
        }
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

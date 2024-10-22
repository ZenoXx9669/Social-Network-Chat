package com.jakenov.Social.Network.Chat.config;

import com.jakenov.Social.Network.Chat.jwt.JwtAuthenticationFilter;
import com.jakenov.Social.Network.Chat.jwt.JwtTokenProvider;
import com.jakenov.Social.Network.Chat.repositories.UserRepository;
import com.jakenov.Social.Network.Chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final JwtAuthenticationFilter authenticationFilter;

    @Autowired
    public SecurityConfig(UserRepository userRepository, JavaMailSender javaMailSender,
                          @Lazy JwtAuthenticationFilter authenticationFilter) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public UserService userService() {
        return new UserService(javaMailSender, userRepository, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userService())
                .passwordEncoder(passwordEncoder());

        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .loginProcessingUrl("/sign-in")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/chat")
                .failureUrl("/login?error")
        ).addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
        );
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}

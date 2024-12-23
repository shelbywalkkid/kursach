package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Используем BCrypt для кодирования паролей
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Отключаем CSRF для тестов (можно позже настроить)
                .authorizeHttpRequests()
                .requestMatchers("/login", "/register", "/admin/login", "/admin/register").permitAll()
                .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
                .and()
                .formLogin()
                .loginPage("/login")  // Страница логина
                .defaultSuccessUrl("/tutorials/list", true)  // Перенаправление после успешного логина
                .permitAll()  // Разрешаем доступ ко всем
                .and()
                .logout()
                .permitAll();  // Разрешаем выход для всех

        return http.build();
    }
}
package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

    public static void main(String[] args) {
        // Создаем экземпляр BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Обычный пароль
        String rawPassword = "admin";

        // Кодируем пароль
        String encodedPassword = encoder.encode(rawPassword);

        // Выводим закодированный пароль
        System.out.println("Encoded password: " + encodedPassword);
    }
}

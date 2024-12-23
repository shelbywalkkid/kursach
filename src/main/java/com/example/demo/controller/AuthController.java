package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Страница логина
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // возвращает шаблон login.html
    }

    // Страница регистрации
    @GetMapping("/register")
    public String registerPage() {
        return "register";  // возвращает шаблон register.html
    }

    // Регистрация нового пользователя
    @PostMapping("/register")
    public User register(@RequestParam String username, @RequestParam String email,
                         @RequestParam String password, @RequestParam String role,
                         @RequestParam(required = false) String fname, @RequestParam(required = false) String sname) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setFname(fname);
        user.setSname(sname);
        return customUserDetailsService.registerUser(user); // Регистрируем пользователя
    }
}

package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Создание нового пользователя
    @PostMapping
    public User createUser(@RequestParam String username, @RequestParam String email,
                           @RequestParam String password, @RequestParam String role,
                           @RequestParam(required = false) String fname, @RequestParam(required = false) String sname) {
        return userService.createUser(username, email, password, role, fname, sname);
    }

    // Получение всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Получение пользователя по имени
    @GetMapping("/{username}")
    public Optional<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    // Получение пользователя по email
    @GetMapping("/email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // Обновление пользователя
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestParam String username,
                           @RequestParam String email, @RequestParam String password,
                           @RequestParam String role, @RequestParam(required = false) String fname,
                           @RequestParam(required = false) String sname) {
        return userService.updateUser(id, username, email, password, role, fname, sname);
    }
}

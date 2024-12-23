package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Получение пользователя по ID
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }
    // Создание нового пользователя
    public User createUser(String username, String email, String password, String role, String fname, String sname) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setFname(fname);
        user.setSname(sname);
        return userRepository.save(user);  // Сохраняем нового пользователя
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Получение пользователя по имени
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Получение пользователя по email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Обновление пользователя
    public User updateUser(Integer id, String username, String email, String password, String role, String fname, String sname) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(role);
            user.setFname(fname);
            user.setSname(sname);
            return userRepository.save(user);
        }

        return null;  // Если пользователь не найден
    }
}
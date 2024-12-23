package com.example.demo.repository;

import com.example.demo.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    // Получаем все рейтинги для определенного туториала
    List<Rating> findByTutorialId(Integer tutorialId);

    // Получаем рейтинг пользователя для определенного туториала
    Optional<Rating> findByTutorialIdAndUserId(Integer tutorialId, Integer userId);
}

package com.example.demo.service;

import com.example.demo.model.Rating;
import com.example.demo.model.Tutorial;
import com.example.demo.model.User;
import com.example.demo.repository.RatingRepository;
import com.example.demo.repository.TutorialRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private UserRepository userRepository;

    // Сохранение нового рейтинга
    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    // Создание нового рейтинга
    public Rating createRating(Integer tutorialId, Integer userId, Short ratingValue) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new RuntimeException("Tutorial not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rating rating = new Rating();
        rating.setTutorial(tutorial);
        rating.setUser(user);
        rating.setRating(ratingValue);
        return ratingRepository.save(rating);
    }

    public Double calculateAverageRating(Integer tutorialId) {
        List<Rating> ratings = ratingRepository.findByTutorialId(tutorialId);

        if (ratings.isEmpty()) {
            return null;  // Если нет оценок, возвращаем null
        }

        double sumRatings = ratings.stream().mapToInt(Rating::getRating).sum();
        return sumRatings / ratings.size();  // Возвращаем среднее значение
    }

    // Получение всех рейтингов для конкретного туториала
    public List<Rating> getRatingsByTutorialId(Integer tutorialId) {
        return ratingRepository.findByTutorialId(tutorialId);  // Используем метод репозитория для получения всех рейтингов
    }

    // Получение рейтинга пользователя для конкретного туториала
    public Optional<Rating> getRatingByTutorialAndUser(Integer tutorialId, Integer userId) {
        return ratingRepository.findByTutorialIdAndUserId(tutorialId, userId);
    }

    // Обновление рейтинга
    public Rating updateRating(Integer tutorialId, Integer userId, Short newRating) {
        Optional<Rating> existingRating = ratingRepository.findByTutorialIdAndUserId(tutorialId, userId);

        if (existingRating.isPresent()) {
            Rating rating = existingRating.get();
            rating.setRating(newRating);
            return ratingRepository.save(rating);
        }

        return null;  // Если рейтинг не найден
    }

}

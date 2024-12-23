package com.example.demo.service;

import com.example.demo.model.Tutorial;
import com.example.demo.repository.TutorialRepository;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingService ratingService;

    // Получение туториала по ID
    public Optional<Tutorial> getTutorialById(Integer tutorialId) {
        return tutorialRepository.findById(tutorialId);
    }

    // Создание нового урока
    public Tutorial createTutorial(String title, String description, String content, Integer authorId) {
        Optional<User> author = userRepository.findById(authorId);

        if (author.isPresent()) {
            Tutorial tutorial = new Tutorial();
            tutorial.setTitle(title);
            tutorial.setDescription(description);
            tutorial.setContent(content);
            tutorial.setAuthor(author.get());
            return tutorialRepository.save(tutorial);  // Сохраняем новый урок
        }

        return null;  // Если автор не найден
    }

    // Метод для сохранения туториала
    public Tutorial saveTutorial(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    // Метод для обновления рейтинга туториала
    public void updateTutorialRating(Integer tutorialId) {
        // Получаем все рейтинги для данного туториала
        List<Rating> ratings = ratingService.getRatingsByTutorialId(tutorialId);

        // Если нет оценок, устанавливаем рейтинг как null
        if (ratings.isEmpty()) {
            Tutorial tutorial = tutorialRepository.findById(tutorialId)
                    .orElseThrow(() -> new RuntimeException("Tutorial not found"));
            tutorial.setAverageRating(null);  // Если рейтингов нет, ставим null
            tutorialRepository.save(tutorial);
            return;
        }

        // Суммируем все рейтинги и рассчитываем средний
        double sumRatings = ratings.stream().mapToInt(Rating::getRating).sum();
        double averageRating = sumRatings / ratings.size();

        // Обновляем рейтинг туториала
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new RuntimeException("Tutorial not found"));
        tutorial.setAverageRating(averageRating);  // Сохраняем средний рейтинг
        tutorialRepository.save(tutorial);
    }

    // Получение всех уроков
    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    // Получение уроков по автору
    public List<Tutorial> getTutorialsByAuthor(Integer authorId) {
        return tutorialRepository.findByAuthorId(authorId);
    }

    // Поиск уроков по названию
    public List<Tutorial> searchTutorialsByTitle(String title) {
        return tutorialRepository.findByTitleContaining(title);
    }

    // Обновление урока
    public Tutorial updateTutorial(Integer id, String title, String description, String content) {
        Optional<Tutorial> existingTutorial = tutorialRepository.findById(id);

        if (existingTutorial.isPresent()) {
            Tutorial tutorial = existingTutorial.get();
            tutorial.setTitle(title);
            tutorial.setDescription(description);
            tutorial.setContent(content);
            tutorial.setUpdatedAt(LocalDateTime.now());  // Обновляем время последнего изменения
            return tutorialRepository.save(tutorial);
        }

        return null;  // Если урок не найден
    }
}

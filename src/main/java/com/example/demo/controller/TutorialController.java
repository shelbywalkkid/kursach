package com.example.demo.controller;

import com.example.demo.model.Tutorial;
import com.example.demo.model.User;

import com.example.demo.repository.TutorialRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RatingService;
import com.example.demo.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/tutorials")
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingService ratingService;

    // Создание нового урока
    @PostMapping("/add")
    public String addTutorial(@ModelAttribute Tutorial tutorial) {
        System.out.println("Controller is called");
        tutorialRepository.save(tutorial);
        return "list";  // После добавления редиректим на список туториалов
    }

    // Обновлённый метод для оценки туториала
    @PostMapping("/{id}/rate")
    public @ResponseBody RatingResponse rateTutorial(@PathVariable Integer id, @RequestParam Integer rating) {
        // Получаем туториал по ID
        Optional<Tutorial> tutorial = tutorialRepository.findById(id);

        if (tutorial.isPresent()) {
            // Обновляем рейтинг туториала
            tutorialService.updateTutorialRating(id);

            // Пересчитываем средний рейтинг
            Double averageRating = ratingService.calculateAverageRating(id);

            // Создаём ответ с обновлённым рейтингом
            RatingResponse response = new RatingResponse();
            response.setAverageRating(averageRating);
            return response;
        }

        return new RatingResponse("error", null);  // В случае ошибки
    }

    @GetMapping("/list")
    public String getAllTutorials(Model model) {
        List<Tutorial> tutorials = tutorialRepository.findAll();  // Получаем все туториалы

        // Для каждого туториала вычисляем средний рейтинг
        for (Tutorial tutorial : tutorials) {
            Double averageRating = ratingService.calculateAverageRating(tutorial.getId());
            tutorial.setAverageRating(averageRating);  // Устанавливаем средний рейтинг
        }

        model.addAttribute("tutorials", tutorials);  // Добавляем данные в модель
        return "list";  // Возвращаем имя шаблона
    }

    // Получение уроков по автору
    @GetMapping("/author/{authorId}")
    public List<Tutorial> getTutorialsByAuthor(@PathVariable Integer authorId) {
        return tutorialService.getTutorialsByAuthor(authorId);
    }

    @GetMapping("/{id}")
    public String getTutorialById(@PathVariable Integer id, Model model) {
        Optional<Tutorial> tutorialOpt = tutorialRepository.findById(id);

        if (tutorialOpt.isPresent()) {
            Tutorial tutorial = tutorialOpt.get();
            Double averageRating = ratingService.calculateAverageRating(id);  // Рассчитываем средний рейтинг
            tutorial.setAverageRating(averageRating);
            model.addAttribute("tutorial", tutorial);  // Добавляем туториал в модель
            return "tutorial";  // Имя вашего шаблона для отображения конкретного туториала
        }

        return "error";  // Страница ошибки, если туториал не найден
    }

    // Поиск уроков по названию
    @GetMapping("/search")
    public List<Tutorial> searchTutorialsByTitle(@RequestParam String title) {
        return tutorialService.searchTutorialsByTitle(title);
    }

    // Обновление урока
    @PutMapping("/{id}")
    public Tutorial updateTutorial(@PathVariable Integer id, @RequestParam String title,
                                   @RequestParam String description, @RequestParam String content) {
        return tutorialService.updateTutorial(id, title, description, content);
    }

    // Вспомогательный класс для ответа с рейтингом
    public static class RatingResponse {
        private String status;
        private Double averageRating;

        public RatingResponse() {}

        public RatingResponse(String status, Double averageRating) {
            this.status = status;
            this.averageRating = averageRating;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(Double averageRating) {
            this.averageRating = averageRating;
        }
    }
}
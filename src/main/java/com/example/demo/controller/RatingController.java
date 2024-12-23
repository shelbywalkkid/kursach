package com.example.demo.controller;

import com.example.demo.model.Rating;
import com.example.demo.model.Tutorial;
import com.example.demo.service.RatingService;
import com.example.demo.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private TutorialService tutorialService;

    // Оценка урока
    @PostMapping("/tutorial/{tutorialId}/user/{userId}")
    public @ResponseBody RatingResponse addRating(@PathVariable Integer tutorialId,
                                                  @PathVariable Integer userId,
                                                  @RequestParam Short rating) {
        try {
            Rating newRating = ratingService.createRating(tutorialId, userId, rating);

            // Пересчитываем средний рейтинг после добавления нового рейтинга
            Double averageRating = ratingService.calculateAverageRating(tutorialId);

            // Создаём ответ с обновлённым рейтингом
            RatingResponse response = new RatingResponse();
            response.setAverageRating(averageRating);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return new RatingResponse("error", null);  // В случае ошибки возвращаем сообщение об ошибке
        }
    }

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

package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Идентификатор

    @Column(nullable = false)
    private Short rating;  // Рейтинг, выставленный пользователем (например, от 1 до 5)

    @ManyToOne
    @JoinColumn(name = "tutorial_id", referencedColumnName = "id", nullable = false)
    private Tutorial tutorial;  // Ссылка на туториал

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;  // Ссылка на пользователя, который поставил рейтинг

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;  // Дата создания рейтинга

    // Конструкторы, геттеры и сеттеры
    public Rating() {
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

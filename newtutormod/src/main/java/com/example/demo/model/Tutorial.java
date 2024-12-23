package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Идентификатор

    @Column(nullable = false)
    private String title;  // Название урока

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;  // Описание урока

    @Column(columnDefinition = "TEXT")
    private String content;  // Содержание урока (будет содержать ссылки на видео)

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;  // Автор урока

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // Время создания

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // Время последнего обновления

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;  // URL изображения

    @Transient
    private Double averageRating;  // Средний рейтинг

    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;  // Комментарии к туториалу

    // Конструкторы, геттеры и сеттеры
    public Tutorial() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Comment> getComments() {
        return comments;
    }

}

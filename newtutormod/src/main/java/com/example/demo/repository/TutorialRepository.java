package com.example.demo.repository;

import com.example.demo.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Integer> {

    // Поиск уроков по автору
    List<Tutorial> findByAuthorId(Integer authorId);

    // Поиск уроков по названию
    List<Tutorial> findByTitleContaining(String title);
}
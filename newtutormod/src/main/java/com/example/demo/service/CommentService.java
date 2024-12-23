package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Tutorial;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TutorialRepository tutorialRepository;

    public Comment addComment(int tutorialId, Comment comment) {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        comment.setId(uuidString);
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new IllegalArgumentException("Tutorial not found"));

        comment.setTutorial(tutorial);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForTutorial(Long tutorialId) {
        return commentRepository.findByTutorialId(tutorialId);
    }
}
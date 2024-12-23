package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Tutorial;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TutorialRepository;
import com.example.demo.service.CommentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/tutorials/{id}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TutorialRepository tutorialRepository;

    @PostMapping
    public void addComment(@PathVariable int id, Comment comment, HttpServletResponse httpServletResponse) {
        System.out.println(comment);
        commentService.addComment(id, comment);
        httpServletResponse.setHeader("Location", "http://localhost:6060/tutorials/" + id);
        httpServletResponse.setStatus(302);
    }

    @GetMapping
    public List<Comment> getComments(@PathVariable Long id) {
        return commentService.getCommentsForTutorial(id);
    }
}

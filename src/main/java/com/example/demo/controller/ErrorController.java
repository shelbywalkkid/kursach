package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public String showErrorPage(Model model) {
        model.addAttribute("errorMessage", "Произошла ошибка");
        return "error";
    }
}
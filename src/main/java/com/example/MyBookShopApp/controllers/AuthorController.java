package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookshop")
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String authorPage(Model model) {
        model.addAttribute("authorsMap" , authorService.getAuthorsMap());
        return "authors/index";
    }
    @GetMapping("/authors/slug")
    public String authorPage(){
        return "authors/slug";
    }
}

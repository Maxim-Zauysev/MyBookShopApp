package com.example.MyBookShopApp.controllers;

<<<<<<< HEAD
=======
import com.example.MyBookShopApp.data.Author;
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056
import com.example.MyBookShopApp.data.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookshop")
public class AuthorController {
<<<<<<< HEAD
    private final AuthorService authorService;
=======
    private AuthorService authorService;
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public String authorPage(Model model) {
<<<<<<< HEAD
        model.addAttribute("authorsMap" , authorService.getAuthorsMap());
=======
        model.addAttribute("authorData",authorService.getAuthorsData());
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056
        return "authors/index";
    }
    @GetMapping("/authors/slug")
    public String authorPage(){
        return "authors/slug";
    }
}

package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;

import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.data.TagEntity;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final TagService tagService;

    @Autowired
    public MainPageController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }

//    @ModelAttribute("popularBooks")
//    public List<Book> bookList() {
//        return bookService.getPageOfPopularBook(0, 6);
//    }

    @ModelAttribute("recentBooks")
    public List<Book> getRecentBooks() {
        return bookService.getRecentBooks(0, 6);
    }

    @ModelAttribute("tags")
    public List<TagEntity> getTags() {
        return tagService.getAllTags();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
            return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        return "index";
    }


    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }
}
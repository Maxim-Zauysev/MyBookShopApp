package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.services.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TagBooksController {

    private final TagService tagService;

    public TagBooksController(TagService tagService) {
        this.tagService = tagService;
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDt() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResult() {
        return new ArrayList<>();
    }

    @GetMapping("/tags/{tagId}")
    public String getPageWithBooksByTag(@PathVariable Integer tagId, Model model) {
        model.addAttribute("booksByTag", tagService.getBooksByTag(tagId, 0, 10).getContent());
        return "/tags/index";
    }

    @GetMapping("/books/tag/{tagId}")
    @ResponseBody
    public BooksPageDto getNextBooksByTagPage(@RequestParam("offset") Integer offset,
                                              @RequestParam("limit") Integer limit,
                                              @PathVariable Integer tagId) {
        return new BooksPageDto(tagService.getBooksByTag(tagId, offset, limit).getContent());
    }
}
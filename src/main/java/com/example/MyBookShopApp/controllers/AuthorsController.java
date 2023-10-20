package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.services.AuthorService;
import com.example.MyBookShopApp.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Api(description = "authors data")
public class AuthorsController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AuthorsController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("authorsMap")
    public Map<String,List<Author>> authorsMap(){
        return authorService.getAuthorsMap();
    }


    @GetMapping("/authors")
    public String authorsPage(){

        return "/authors/index";
    }

    @GetMapping(value = "/authors/{slug}", produces = MediaType.TEXT_HTML_VALUE)
    public String authorPage(@PathVariable("slug") String slug, Model model){
        model.addAttribute("author", authorService.getAuthorBySlug(slug));
        model.addAttribute("authorBooks", bookService.getPageBooksByAuthorSlug(slug,0,10));
        return "authors/slug";
    }

    @GetMapping(value = "/books/author/{slug}", produces = MediaType.TEXT_HTML_VALUE)
    public String getAuthorBooksPage(@PathVariable("slug") String slug, Model model){
        model.addAttribute("author", authorService.getAuthorBySlug(slug));
        model.addAttribute("authorBooks", bookService.getPageBooksByAuthorSlug(slug,0,10));
        return "books/author";
    }

    @GetMapping(value = "/books/author/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getNewAuthorBooksPage(@RequestParam("offset") Integer offset,
                                              @RequestParam("limit") Integer limit,
                                              @PathVariable(value = "slug") String slug) {
        return new BooksPageDto(bookService.getPageBooksByAuthorSlug(slug, offset, limit).getContent());
    }

    @ApiOperation("method to get map of authors")
    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String, List<Author>> authors(){
        return authorService.getAuthorsMap();
    }
}
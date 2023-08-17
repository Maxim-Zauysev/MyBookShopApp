package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PopularPageController {

    private final BookService bookService;

    @Autowired
    public PopularPageController(BookService bookService) {
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

    @ModelAttribute("popularList")
    public List<Book> bookList(){
        return bookService.getPageOfPopularBook(0,10);
    }

    @GetMapping(value = "/books/popular", produces = MediaType.TEXT_HTML_VALUE)
    public String popularPage() {
        return "/books/popular";
    }

    @GetMapping(value = "/books/popular", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getPopularityBooks(@RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit){
        return new BooksPageDto(bookService.getPageOfPopularBook(offset, limit));
    }
}

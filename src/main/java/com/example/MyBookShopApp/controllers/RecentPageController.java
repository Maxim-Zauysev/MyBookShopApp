package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import com.example.MyBookShopApp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class RecentPageController {
    private final BookService bookService;
    @Autowired
    public RecentPageController(BookService bookService) {
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

    @ModelAttribute("recentBooks")
    public List<Book> getRecentBooks(){
        return bookService.getRecentBooks(0, 10);
    }

    @GetMapping(value = "/books/recent", produces = MediaType.TEXT_HTML_VALUE)
    public String getRecent(Model model) {
        String dateFrom = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String dateTo = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        model.addAttribute("recent",bookService.getPageOfRecentBooksByDate(dateFrom, dateTo, 0, 10));
        return "/books/recent";
    }

    @GetMapping(value = "/books/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getRecentBook(@RequestParam(value = "from", required = false) String from,
                                     @RequestParam(value = "to", required = false) String to,
                                     @RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecentBooksByDate(from, to, offset, limit));
    }

    @GetMapping(value = "books/slider/recent", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getRecentSliderBook(
                                      @RequestParam("offset") Integer offset,
                                      @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getRecentBooks(offset, limit));
    }
}

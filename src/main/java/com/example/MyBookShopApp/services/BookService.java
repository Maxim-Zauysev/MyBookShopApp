package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class BookService {

    private final DateFormat oldDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private final DateFormat newDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthor(String authorName){
        return bookRepository.findBooksByAuthorNameContaining(authorName);
    }

    public List<Book> getBooksByTitle(String title){
        return bookRepository.findBooksByTitleContaining(title);
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max){
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price){
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPrice(){
        return bookRepository.getBooksWithMaxDiscount();
    }

    public List<Book> getBestsellers(){
        return bookRepository.getBestsellers();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findAll(nextPage);
    }

    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset,limit);
        return bookRepository.findBookByTitleContaining(searchWord,nextPage);
    }

    public List<Book> getPageOfPopularBook(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);

        return bookRepository.getPopularityBooks(nextPage);
    }

    public List<Book> getRecentBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        return bookRepository.findAll(nextPage).getContent();
    }

    public List<Book> getPageOfRecentBooksByDate(String from, String to, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        Date fromDate = new Date();
        Date toDate = new Date();
        try {
                fromDate = oldDateFormat.parse(from);
                toDate = oldDateFormat.parse(to);
                String newDateFrom = newDateFormat.format(fromDate);
                String newDateTo = newDateFormat.format(toDate);
                fromDate = newDateFormat.parse(newDateFrom);
                toDate = newDateFormat.parse(newDateTo);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return bookRepository.findBooksByPubDateBetween(fromDate, toDate, nextPage);
    }
}



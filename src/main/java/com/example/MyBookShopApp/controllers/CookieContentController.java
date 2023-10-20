package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class CookieContentController {

    private final BookRepository bookRepository;

    @Autowired
    public CookieContentController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart(){
        return new ArrayList<>();
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        return handleRequest(cartContents, model, "isCartEmpty", "cart", "bookCart");
    }

    @GetMapping("/postponed")
    public String postponedPage(@CookieValue(value = "postponedContents", required = false) String postponedContents,
                                Model model) {
        return handleRequest(postponedContents, model, "isPostponedEmpty", "postponed", "bookPostponed");
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug,
                                         @CookieValue(name = "cartContents",required = false)
                                         String cartContents,
                                         HttpServletResponse response, Model model){
        return handleBookStatusChange(slug, "cartContents", cartContents, response, model, "isCartEmpty");
    }

    @PostMapping("/changeBookStatus/postponed/{slug}")
    public String handleChangeBookStatusPostponed(@PathVariable("slug") String slug,
                                                  @CookieValue(name = "postponedContents",required = false)
                                                  String postponedContents,
                                                  HttpServletResponse response, Model model){
        return handleBookStatusChange(slug, "postponedContents", postponedContents, response, model, "isPostponedEmpty");
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "cartContents", required = false) String cartContents, HttpServletResponse response, Model model) {
        return handleRemoveBookRequest(slug, "cartContents", cartContents, response, model, "isCartEmpty", "/books/cart");
    }

    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public String handleRemoveBookFromPostponedRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "postponedContents", required = false) String postponedContents, HttpServletResponse response, Model model){
        return handleRemoveBookRequest(slug, "postponedContents", postponedContents, response, model, "isPostponedEmpty", "/books/postponed");
    }

    private String handleRequest(String cookieContents, Model model, String attribute, String viewName, String bookAttribute) {
        if (cookieContents == null || cookieContents.equals("")) {
            model.addAttribute(attribute, true);
            model.addAttribute("sumPriceOld", 0);
            model.addAttribute("sumPrice", 0);
        } else {
            model.addAttribute(attribute, false);
            cookieContents = cookieContents.startsWith("/") ? cookieContents.substring(1) : cookieContents;
            cookieContents = cookieContents.endsWith("/") ? cookieContents.substring(0, cookieContents.length() - 1) :
                    cookieContents;
            String[] cookieSlugs = cookieContents.split("/");
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute(bookAttribute, booksFromCookieSlugs);

            int sumPriceOld = 0;
            int sumPrice = 0;

            for (Book book: booksFromCookieSlugs) {
                sumPriceOld += book.getPriceOld();
                sumPrice += book.discountPrice();
            }
            model.addAttribute("sumPriceOld", sumPriceOld);
            model.addAttribute("sumPrice", sumPrice);

        }

        return viewName;
    }

    private String handleBookStatusChange(String slug, String cookieName, String contents, HttpServletResponse response, Model model, String attribute) {
        if (contents == null || contents.equals("")) {
            Cookie cookie = new Cookie(cookieName, slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute(attribute, false);
        } else if (!contents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(contents).add(slug);
            Cookie cookie = new Cookie(cookieName, stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute(attribute, false);
        }
        return "redirect:/book/" + slug;
    }

    private String handleRemoveBookRequest(String slug, String cookieName, String contents, HttpServletResponse response, Model model, String attribute, String redirectUrl) {
        if (contents != null && !contents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(contents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie(cookieName, String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute(attribute, false);
        } else {
            model.addAttribute(attribute, true);
        }

        return "redirect:" + redirectUrl;
    }



}

package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.ResourceStorage;
import com.example.MyBookShopApp.data.book.review.BookRatingEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;


import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Controller
@RequestMapping("/book")
public class BooksController {

    private final BookRepository bookRepository;
    private final ResourceStorage storage;
    private final BookReviewRepository reviewRepository;
    private final BookRatingRepository ratingRepository;
    private final UserRepository userRepository;

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @Autowired
    public BooksController(BookRepository bookRepository, ResourceStorage storage, BookReviewRepository reviewRepository, BookRatingRepository ratingRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.storage = storage;
        this.reviewRepository = reviewRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable("slug") String slug, Model model) {
        Book book = bookRepository.findBookBySlug(slug);
        model.addAttribute("slugBook", book);
        model.addAttribute("oneRating", bookRepository.getRatingCountByBookId(1l,book.getId()));
        model.addAttribute("twoRating", bookRepository.getRatingCountByBookId(2l,book.getId()));
        model.addAttribute("threeRating", bookRepository.getRatingCountByBookId(3l,book.getId()));
        model.addAttribute("fourRating", bookRepository.getRatingCountByBookId(4l,book.getId()));
        model.addAttribute("fiveRating", bookRepository.getRatingCountByBookId(5l,book.getId()));
        return "/books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file")MultipartFile file,@PathVariable("slug")String slug) throws IOException {
        String savePath = storage.saveNewBookImage(file,slug);
        Book bookToUpdate = bookRepository.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookRepository.save(bookToUpdate);

        return "redirect:/book/"+slug;
    }

    @PostMapping("/{slug}/review/save")
    public String saveReview(@PathVariable("slug") String slug,
                             @RequestParam String text,
                             @RequestParam("rating") Integer rating){
        Book book = bookRepository.findBookBySlug(slug);

        BookRatingEntity bookRating = new BookRatingEntity();
        bookRating.setRating(rating);
        bookRating.setBook(book);
        bookRating.setUser(null);
        ratingRepository.save(bookRating);

        BookReviewEntity bookReview = new BookReviewEntity();
        bookReview.setBookRatingEntity(bookRating);
        bookReview.setText(text);
        bookReview.setTime(LocalDateTime.now());
        reviewRepository.save(bookReview);

        return "redirect:/book/" + slug;
    }



    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {
        Path path = storage.getBookFilePath(hash);
        Logger.getLogger((this.getClass().getSimpleName())).info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger((this.getClass().getSimpleName())).info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger((this.getClass().getSimpleName())).info("book file data len: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

}

package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRepositoryTests {

    private final BookRepository bookRepository;

    @Autowired
    BookRepositoryTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Transactional
    @Test
    void findBookByTitleContaining() {
        String token = "Sun Kissed";
        List<Book> books = bookRepository.findBookByTitleContaining(token);
        assertNotNull(books);
        assertFalse(books.isEmpty());
        for (Book book : books) {
            Hibernate.initialize(book.getTags());
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            assertThat(book.getTitle()).contains(token);
        }
    }


    @Test
    void getBestsellers() {
        List<Book> books = bookRepository.getBestsellers();
        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Transactional
    @Test
    void findBooksByAuthorNameContaining() {

        String token = "Paullin";
        List<Book> bookList = bookRepository.findBooksByAuthorNameContaining(token);
        
        assertNotNull(bookList);
        assertFalse(bookList.isEmpty());

        for (Book book : bookList) {
            Logger.getLogger(this.getClass().getSimpleName()).info(book.toString());
            List<Author> authors = book.getAuthors();

            if (!authors.isEmpty() && authors != null) {
                for (Author author : authors) {
                    assertThat(author.getName().contains(token));
                }
            }

        }
    }
}
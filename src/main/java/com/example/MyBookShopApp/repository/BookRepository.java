package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findBooksByAuthor_Name(String name);

    @Query("from Book")
    List<Book> customFindAllBooks();

    List<Book> findBooksByAuthorNameContaining(String authorsName);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)",nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();
    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

}

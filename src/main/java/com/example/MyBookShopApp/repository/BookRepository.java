package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findBookByAuthor_FirstName(String name);
    @Query("from Book")
    List<Book> customFindAllBooks();
}

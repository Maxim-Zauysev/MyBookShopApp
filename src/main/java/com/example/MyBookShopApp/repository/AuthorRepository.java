package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository  extends JpaRepository<Author,Long> {

}

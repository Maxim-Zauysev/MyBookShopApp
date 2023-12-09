package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.security.BookStoreUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookStoreUserRepository extends JpaRepository<BookStoreUser, Integer> {

    BookStoreUser findBookStoreUserByEmail(String email);
}

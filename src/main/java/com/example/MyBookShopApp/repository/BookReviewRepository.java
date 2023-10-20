package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.review.BookRatingEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Long> {
}

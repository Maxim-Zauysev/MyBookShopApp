package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.review.BookRatingEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReviewEntity, Long> {
}

package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.book.review.BookRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRatingRepository extends JpaRepository<BookRatingEntity, Long> {

}

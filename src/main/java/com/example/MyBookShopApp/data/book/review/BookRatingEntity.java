package com.example.MyBookShopApp.data.book.review;
import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

    @Entity
    @Table(name = "book_rating")
    public class BookRatingEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "book_id", referencedColumnName = "id")
        @JsonIgnore
        private Book book;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        @JsonIgnore
        private UserEntity user;

        @Column(columnDefinition = "SMALLINT NOT NULL")
        private int rating;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        public UserEntity getUser() {
            return user;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }


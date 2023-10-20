package com.example.MyBookShopApp.data.book.review;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "book_review")
public class BookReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDateTime time;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;

    @OneToOne
    @JoinColumn(name = "rating_id",referencedColumnName = "id")
    private BookRatingEntity bookRatingEntity;

    @OneToMany(mappedBy = "bookReviewEntity")
    @JsonIgnore
    private List<BookReviewLikeEntity> bookReviewLikeEntities;

    @PrePersist
    public void prePersist() {
        this.time = LocalDateTime.now();
    }

    public BookReviewEntity() {
    }

    public Integer getLikesCount() {
        int result = 0;
        for (BookReviewLikeEntity like : getBookReviewLikeEntities()) {
            if (like.getValue() == 1) result++;
        }
        return result;
    }

    public Integer getDislikesCount() {
        int result = 0;
        for (BookReviewLikeEntity like : getBookReviewLikeEntities()) {
            if (like.getValue() == -1) result++;
        }
        return result;
    }

    public String getStringTime() {
        return getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public BookRatingEntity getBookRatingEntity() {
        return bookRatingEntity;
    }

    public void setBookRatingEntity(BookRatingEntity bookRatingEntity) {
        this.bookRatingEntity = bookRatingEntity;
    }

    public List<BookReviewLikeEntity> getBookReviewLikeEntities() {
        return bookReviewLikeEntities;
    }

    public void setBookReviewLikeEntities(List<BookReviewLikeEntity> bookReviewLikeEntities) {
        this.bookReviewLikeEntities = bookReviewLikeEntities;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

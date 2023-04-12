package com.example.MyBookShopApp.data.book.links;

import javax.persistence.*;

@Entity
@Table(name = "book2tag")
public class Book2TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "book_id", columnDefinition = "INT", nullable = false)
    private Integer bookId;

    @Column(name = "tag_id", columnDefinition = "INT", nullable = false)
    private Integer tagId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}

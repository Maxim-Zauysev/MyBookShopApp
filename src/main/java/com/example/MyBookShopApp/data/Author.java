package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.book.links.Book2AuthorEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;



import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@ApiModel(description = "data model of author entity")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "photo", columnDefinition = "VARCHAR(255)")
    private String photo;
    @Column(name = "slug", columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;
    @Column(name = "name", columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy = "author")
    private List<Book> bookList = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return   name;
    }

}

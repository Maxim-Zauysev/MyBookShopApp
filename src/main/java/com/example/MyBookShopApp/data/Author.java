package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.data.book.links.Book2AuthorEntity;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
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

    public Author() {
    }

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
}

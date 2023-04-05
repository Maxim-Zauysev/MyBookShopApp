package com.example.MyBookShopApp.data;


import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.book.links.Book2AuthorEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@ApiModel(description = "entity representing a book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id generated by db automatically")
    private Long id;

    @Column(name = "pub_date", columnDefinition = "DATE NOT NULL")
    @ApiModelProperty("date of book publication")
    private Date pubDate;

    @Column(name = "is_bestseller", columnDefinition = "SMALLINT NOT NULL")
    @ApiModelProperty("if isBestseller = 1 so the book is considered to be bestseller and if 0 the book is not a " +
            "bestseller")
    private Integer isBestseller;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    @ApiModelProperty("mnemonical identity sequence of characters")
    private String slug;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    @ApiModelProperty("book title")
    private String title;

    @Column(columnDefinition = "VARCHAR(255)")
    @ApiModelProperty("book url")
    private String image;

    @Column(columnDefinition = "TEXT")
    @ApiModelProperty("book description text")
    private String description;

    @Column(name = "price")
    @JsonProperty("price")
    @ApiModelProperty("book price without discount")
    private Integer priceOld;

    @Column(name = "discount")
    @JsonProperty("discount")
    @ApiModelProperty("discount value for book")
    private Double price;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnore
    private Author author;


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Integer getIsBestseller() {
        return isBestseller;
    }

    public void setIsBestseller(Integer isBestseller) {
        this.isBestseller = isBestseller;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(Integer priceOld) {
        this.priceOld = priceOld;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

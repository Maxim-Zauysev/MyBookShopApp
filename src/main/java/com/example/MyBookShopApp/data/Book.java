package com.example.MyBookShopApp.data;


import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.review.BookRatingEntity;
import com.example.MyBookShopApp.data.book.review.BookReviewEntity;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.data.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import javax.persistence.*;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    /*
    Использовал триггер
    BEGIN
      UPDATE books SET rating = (SELECT AVG(rating) FROM book_rating WHERE book_id = NEW.book_id) WHERE id = NEW.book_id;
      RETURN NEW;
    END;

    UPDATE books b
    SET rating = (SELECT AVG(br.rating) FROM book_rating br WHERE b.id = br.book_id);
     */
    @Column(name = "rating")
    @JsonProperty("rating")
    @ApiModelProperty("book rating")
    private Double rating;

    @Column(name = "discount")
    @JsonProperty("discount")
    @ApiModelProperty("discount value for book")
    private Double price;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 5)
    private List<TagEntity> tags;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 5)
    private List<GenreEntity> genres;

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    @JsonIgnore
    @BatchSize(size = 5)
    private List<Author> authors;


    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Book2UserEntity> bookUsers;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<BookFile> bookFileList = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    @ApiModelProperty("book rating entities")
    @JsonIgnore
    private List<BookRatingEntity> bookRatingEntities;

    @JsonProperty
    public Integer discountPrice(){
        Integer discountedPrice = priceOld - Math.toIntExact(Math.round(priceOld*price));
        return discountedPrice;
    }

    @JsonGetter("authors")
    public List<String> authorsFullName(){
        return authors.stream()
                .map(Author::getName)
                .collect(Collectors.toList());
    }

    @JsonGetter("pub")
    public String pub(){
        return new SimpleDateFormat("yyyy/MM/dd").format(pubDate);
    }


    @JsonIgnore
    public List<BookReviewEntity> getReviewList() {
        List<BookReviewEntity> list = new ArrayList<>();
        for (BookRatingEntity ratingEntity : getBookRatingEntities()) {
            if (ratingEntity.getBookReviewEntity() != null) list.add(ratingEntity.getBookReviewEntity());
        }
        return list;
    }

//    @Override
//    public String toString() {
//        return "Book{" +
//                "id=" + id +
//                ", isBestseller=" + isBestseller +
//                ", title='" + title + '\'' +
//                ", rating=" + rating +
//                ", tags=" + tags +
//                ", authors=" + authors +
//                '}';
//    }

    public List<BookFile> getBookFileList() {
        return bookFileList;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setBookFileList(List<BookFile> bookFileList) {
        this.bookFileList = bookFileList;
    }

    public List<Book2UserEntity> getBookUsers() {
        return bookUsers;
    }

    public void setBookUsers(List<Book2UserEntity> bookUsers) {
        this.bookUsers = bookUsers;
    }

    public List<TagEntity> getTagEntityList() {
        return tags;
    }

    public void setTagEntityList(List<TagEntity> tagEntityEntityList) {
        this.tags = tagEntityEntityList;
    }

    public List<BookRatingEntity> getBookRatingEntities() {
        return bookRatingEntities;
    }

    public void setBookRatingEntities(List<BookRatingEntity> bookRatingEntities) {
        this.bookRatingEntities = bookRatingEntities;
    }

    public List<TagEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }

    public List<GenreEntity> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreEntity> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
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

package com.example.MyBookShopApp.data.user;

import com.example.MyBookShopApp.data.book.links.Book2UserEntity;
import com.example.MyBookShopApp.data.book.review.BookRatingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(columnDefinition = "DATE", nullable = false)
    private Date regTime;

    @Column(columnDefinition = "INT NOT NULL")
    private int balance;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String name;

    private String email;
    private String phone;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Book2UserEntity> bookUsers ;

    @OneToMany(mappedBy = "user")
    @ApiModelProperty("book rating entities")
    @JsonIgnore
    private List<BookRatingEntity> bookRatingEntities;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book2UserEntity> getBookUsers() {
        return bookUsers;
    }

    public void setBookUsers(List<Book2UserEntity> bookUsers) {
        this.bookUsers = bookUsers;
    }

    public List<BookRatingEntity> getBookRatingEntities() {
        return bookRatingEntities;
    }

    public void setBookRatingEntities(List<BookRatingEntity> bookRatingEntities) {
        this.bookRatingEntities = bookRatingEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return id == user.id && balance == user.balance && Objects.equals(hash, user.hash) && Objects.equals(regTime, user.regTime) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(password, user.password) && Objects.equals(bookUsers, user.bookUsers) && Objects.equals(bookRatingEntities, user.bookRatingEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hash, regTime, balance, name, email, phone, password, bookUsers, bookRatingEntities);
    }
}

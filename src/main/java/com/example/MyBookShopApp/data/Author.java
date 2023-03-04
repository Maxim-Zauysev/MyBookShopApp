package com.example.MyBookShopApp.data;

public class Author {
    private Integer id;
    private String firstName;
    private String lastName;

<<<<<<< HEAD
    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
=======
    @Override
    public String toString() {
        return firstName + ' ' + lastName;
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

<<<<<<< HEAD
    public void setLastName(String lastName) {
        this.lastName = lastName;
=======
    public void setLastName(String secondName) {
        this.lastName = secondName;
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056
    }
}

package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
<<<<<<< HEAD
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056

@Service
public class AuthorService {
    private JdbcTemplate jdbcTemplate;
<<<<<<< HEAD
=======

>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056
    @Autowired
    public AuthorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
<<<<<<< HEAD
    public Map<String, List<Author>> getAuthorsMap(){
        List<Author> authots = jdbcTemplate.query("SELECT * FROM authors", (ResultSet rs, int rownum)->{
=======

    public List<Author> getAuthorsData() {
        List<Author> authors = jdbcTemplate.query("SELECT * FROM authors", (ResultSet rs, int rowNum)->{
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setFirstName(rs.getString("firstName"));
            author.setLastName(rs.getString("lastName"));
            return author;
        });
<<<<<<< HEAD

        return authots.stream().collect(Collectors.groupingBy((Author a)->{return a.getLastName().substring(0,1);}));
=======
        return new ArrayList<>(authors);
>>>>>>> 5d04d0c88ff344294e15b99237cce84acea8b056
    }
}

package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class UserRepositoryTests {

    private final UserRepository userRepository;
    private final DateFormat newDateFormat =  BookstoreUserRegister.newDateFormat;

    @Autowired
    UserRepositoryTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void testAddNewUser() throws ParseException {
        UserEntity user = new UserEntity();
        Date date = new Date();
        String newDateFrom = newDateFormat.format(date);
        date = newDateFormat.parse(newDateFrom);

        user.setName("Tester");
        user.setEmail("dataTest@mail.com");
        user.setPhone("89234517922");
        user.setPassword("admin");
        user.setHash(Integer.toString(user.hashCode()));
        user.setBalance(0);
        user.setRegTime(date);

        assertNotNull(userRepository.save(user));
    }
}
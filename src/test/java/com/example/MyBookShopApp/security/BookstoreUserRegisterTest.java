package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookstoreUserRegisterTest {

    private final BookstoreUserRegister userRegister;
    private final PasswordEncoder passwordEncoder;
    private RegistrationForm registrationForm;

    @MockBean
    private UserRepository userRepository;
    @Autowired
    BookstoreUserRegisterTest(BookstoreUserRegister userRegister, PasswordEncoder passwordEncoder) {
        this.userRegister = userRegister;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegistrationForm();
        registrationForm.setEmail("test@mail.ru");
        registrationForm.setName("tester");
        registrationForm.setPass("admin");
        registrationForm.setPhone("890234517922");

    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void registerNewUser() throws ParseException {
        UserEntity user = userRegister.registerNewUser(registrationForm);
        assertNotNull(user);
        assertTrue(passwordEncoder.matches(registrationForm.getPass(),user.getPassword()));
        assertTrue(CoreMatchers.is(user.getPhone()).matches(registrationForm.getPhone()));
        assertTrue(CoreMatchers.is(user.getEmail()).matches(registrationForm.getEmail()));
        assertTrue(CoreMatchers.is(user.getName()).matches(registrationForm.getName()));

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    void registerNewUserFail() throws ParseException {
        Mockito.doReturn(new UserEntity())
                .when(userRepository)
                .findUserEntityByEmail(registrationForm.getEmail());

        UserEntity user = userRegister.registerNewUser(registrationForm);
        assertNull(user);

    }
}
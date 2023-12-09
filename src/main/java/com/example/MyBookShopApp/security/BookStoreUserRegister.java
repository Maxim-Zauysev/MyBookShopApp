package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.repository.BookStoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
public class BookStoreUserRegister {

    private final BookStoreUserRepository bookStoreUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public BookStoreUserRegister(BookStoreUserRepository bookStoreUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.bookStoreUserRepository = bookStoreUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void registerNewUser(RegistrationForm registrationForm){
        if (bookStoreUserRepository.findBookStoreUserByEmail(registrationForm.getEmail()) == null){
            BookStoreUser user = new BookStoreUser();
            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getEmail());
            user.setPhone(registrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
            bookStoreUserRepository.save(user);
        }

    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(), payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(true);
        return response;
    }

    public Object getCurrentUser() {
        BookStoreUserDetail userDetail = (BookStoreUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getBookStoreUser();
    }
}

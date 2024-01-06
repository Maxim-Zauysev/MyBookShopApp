package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repository.UserRepository;
import com.example.MyBookShopApp.security.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BookstoreUserRegister {

    private final UserRepository bookstoreUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    public static final DateFormat newDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private final JWTUtil jwtUtil;

    @Autowired
    public BookstoreUserRegister(UserRepository bookstoreUserRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public void registerNewUser(RegistrationForm registrationForm)  {
        UserEntity byEmail = bookstoreUserRepository.findUserEntityByEmail(registrationForm.getEmail());
        UserEntity byPhone = bookstoreUserRepository.findUserEntityByPhone(registrationForm.getPhone());


        if (byEmail == null && byPhone == null) {
            UserEntity user = new UserEntity();
            Date date = new Date();
            String newDateFrom = newDateFormat.format(date);
            try {
                date = newDateFormat.parse(newDateFrom);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            user.setName(registrationForm.getName());
            user.setEmail(registrationForm.getEmail());
            user.setPhone(registrationForm.getPhone());
            user.setPassword(passwordEncoder.encode(registrationForm.getPass()));
            user.setHash(Integer.toString(user.hashCode()));
            user.setBalance(0);
            user.setRegTime(date);

            bookstoreUserRepository.save(user);


        }

    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                        payload.getCode()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());

        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }

    public Object getCurrentUser() {
        BookstoreUserDetails userDetails =
                (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getBookstoreUser();
    }

    public ContactConfirmationResponse jwtLoginByPhoneNumber(ContactConfirmationPayload payload) {
        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setPhone(payload.getContact());
        registrationForm.setPass(payload.getCode());
        registerNewUser(registrationForm);
        UserDetails userDetails =  bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult(jwtToken);
        return response;
    }
}

package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import com.example.MyBookShopApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookstoreUserDetailsService implements UserDetailsService {

    private final UserRepository bookstoreUserRepository;

    @Autowired
    public BookstoreUserDetailsService(UserRepository bookstoreUserRepository) {
        this.bookstoreUserRepository = bookstoreUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity bookstoreUser = bookstoreUserRepository.findUserEntityByEmail(s);
        if(bookstoreUser!=null){
            return new BookstoreUserDetails(bookstoreUser);
        }

        bookstoreUser = bookstoreUserRepository.findUserEntityByPhone(s);
        if(bookstoreUser !=null){
            return new PhoneNumberUserDetails(bookstoreUser);
        }else {
            throw new UsernameNotFoundException("user not fount");
        }
    }
}

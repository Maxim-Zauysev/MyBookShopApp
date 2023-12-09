package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class BookStoreUserDetail implements UserDetails {

    private final BookStoreUser bookStoreUser;

    public BookStoreUserDetail(BookStoreUser bookStoreUser) {
        this.bookStoreUser = bookStoreUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return bookStoreUser.getPassword();
    }

    @Override
    public String getUsername() {
        return bookStoreUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public BookStoreUser getBookStoreUser() {
        return bookStoreUser;
    }
}

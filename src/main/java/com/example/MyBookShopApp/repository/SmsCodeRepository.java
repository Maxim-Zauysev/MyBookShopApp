package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.security.SmsCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {
    public SmsCode findByCode(String code);
}

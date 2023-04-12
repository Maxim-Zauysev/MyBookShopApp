package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagEntityRepository extends JpaRepository<TagEntity, Integer> {
    TagEntity findTagEntityById(Integer id);

}
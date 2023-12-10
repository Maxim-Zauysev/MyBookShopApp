package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagEntityRepository extends JpaRepository<TagEntity, Integer> {

    TagEntity findTagEntityById(Integer id);

}
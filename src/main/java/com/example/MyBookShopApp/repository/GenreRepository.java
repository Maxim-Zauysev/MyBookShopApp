package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity,Integer>
{
   GenreEntity findGenreEntityById(Integer id);
   List<GenreEntity> findAllByParentId(Integer parentId);

}

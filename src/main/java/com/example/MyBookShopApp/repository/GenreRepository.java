package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<GenreEntity,Integer>
{
   GenreEntity findGenreEntityById(Integer id);
   List<GenreEntity> findAllByParentId(Integer parentId);

   //получить поджанры жанра
   @Query(value = "SELECT * FROM genre WHERE parent_id = :parentId", nativeQuery = true)
   List<GenreEntity> findSubgenresByParentId(Integer parentId);

}

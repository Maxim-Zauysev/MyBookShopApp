package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity,Integer>
{
   @Query(nativeQuery = true, value = "SELECT * FROM genre WHERE slug = :slug")
   GenreEntity findGenreEntityBySlug(String slug);

   List<GenreEntity> findAllByParentId(Integer parentId);

   //получить поджанры жанра
   @Query(value = "SELECT * FROM genre WHERE parent_id = :parentId", nativeQuery = true)
   List<GenreEntity> findSubgenresByParentId(Integer parentId);

}

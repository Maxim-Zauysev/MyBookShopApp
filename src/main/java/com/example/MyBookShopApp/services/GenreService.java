package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public List<GenreEntity> getSubgenresPyParentGenre(GenreEntity parent){
        return genreRepository.findSubgenresByParentId(parent.getId());
    }
    public  GenreEntity getGenreBySlug(String slug){
        return genreRepository.findGenreEntityBySlug(slug);
    }
    //Все родители 
    public List<GenreEntity> getAllParents(){
        return genreRepository.findAllByParentId(null);
    }

    public Page<Book> getBooksByGenreSlug(String slug, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getBooksByGenreSlug(slug, nextPage);
    }

    public Map<GenreEntity, List<GenreEntity>> getGenreMap() {
        List<GenreEntity> parentGenres = getAllParents();
        Map<GenreEntity, List<GenreEntity>> genreMap = new LinkedHashMap<>();

        for (GenreEntity parentGenre : parentGenres) {
            List<GenreEntity> subgenres = genreRepository.findSubgenresByParentId(parentGenre.getId());
            genreMap.put(parentGenre, subgenres);
        }
        return genreMap;
    }
}

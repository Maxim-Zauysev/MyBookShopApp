package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    //получить все книги жанра и его поджанров
    public void getAllBooks(){
        List<Book> books = new ArrayList<>();
        for(GenreEntity genre :getGenresByIdWithSubgenres(1)){
            books.addAll(bookRepository.getBooksByGenre(genre.getId()));
        }

    }

    //получить список поджанров
    public List<GenreEntity> getGenresByIdWithSubgenres(Integer genreId) {
        List<GenreEntity> genres = new ArrayList<>();
        fetchGenresWithSubgenres(genreId, genres);
        return genres;
    }

    private void fetchGenresWithSubgenres(Integer genreId, List<GenreEntity> genres) {
        GenreEntity genre = genreRepository.findGenreEntityById(genreId);
        if (genre != null) {
            genres.add(genre);
            List<GenreEntity> subgenres = genreRepository.findAllByParentId(genreId);
            for (GenreEntity subgenre : subgenres) {
                fetchGenresWithSubgenres(subgenre.getId(), genres);
            }
        }
    }

}

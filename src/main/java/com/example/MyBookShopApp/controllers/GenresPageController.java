package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GenresPageController {
    private final GenreService genreService;

    @Autowired
    public GenresPageController(GenreService genreService) {
        this.genreService = genreService;
    }

    @ModelAttribute("parents")
    public List<GenreEntity> parentsAttribute(){
        return genreService.getAllParents();
    }

    @GetMapping("/genres")
    public String genresPage(Model model) {
        Map<GenreEntity, List<GenreEntity>> genreMap = genreService.getGenreMap();
        model.addAttribute("genreMap", genreMap);
        return "/genres/index";
    }

    @GetMapping(value = "/genres/{slug}",  produces = MediaType.TEXT_HTML_VALUE)
    public String genrePage(@PathVariable String slug, Model model) {
        model.addAttribute("booksByGenre", genreService.getBooksByGenreSlug(slug, 0, 10).getContent());
        GenreEntity currentGenre = genreService.getGenreBySlug(slug);

        if(currentGenre != null) {
            model.addAttribute("currentGenre",currentGenre);
            model.addAttribute("subgenres", genreService.getSubgenresPyParentGenre(currentGenre));
        }else
            throw new NullPointerException("genre is null /genres/slug");

        return "/genres/slug";
    }

    @GetMapping(value = "/books/genre/{slug}")
    @ResponseBody
    public BooksPageDto getPageWithBooksByGenrePage(@RequestParam("offset") Integer offset,
                                                    @RequestParam("limit") Integer limit,
                                                    @PathVariable(value = "slug") String slug){
        return new BooksPageDto(genreService.getBooksByGenreSlug(slug,offset,limit).getContent());
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }
}

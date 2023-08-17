package com.example.MyBookShopApp.services;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.TagEntity;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.repository.TagEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TagService {
    private final TagEntityRepository tagRepository;
    private final BookRepository bookRepository;

    @Autowired
    public TagService(TagEntityRepository tagRepository, BookRepository bookRepository) {
        this.tagRepository = tagRepository;
        this.bookRepository = bookRepository;
    }

    public List<TagEntity> getAllTags(){
        return tagRepository.findAll();
    }

    public Page<Book> getBooksByTag(Integer tagId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getBooksByTag(tagId, nextPage);
    }

    public String getTagNameById(Integer tagId){
        return tagRepository.findTagEntityById(tagId).getName();
    }
}

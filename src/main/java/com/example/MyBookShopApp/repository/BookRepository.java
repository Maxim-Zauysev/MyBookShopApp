package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.TagEntity;
import liquibase.pro.packaged.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    Book findBooksById(Integer id);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(nativeQuery = true, value = "SELECT books.*\n" +
            "FROM books\n" +
            "INNER JOIN book2author ON books.id = book2author.book_id\n" +
            "INNER JOIN author ON book2author.author_id = author.id\n" +
            "WHERE author.name=:authorsName")
    List<Book> findBooksByAuthorNameContaining(String authorsName);

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)",nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

    List<Book> findBooksByPubDateBetween(Date from, Date to, Pageable nextPage);

    @Query(value = "select * from books b join book2tag bt on b.id=bt.book_id join tags t on bt.tag_id=t.id where t.id=:tagId", nativeQuery = true)
    Page<Book> getBooksByTag(Integer tagId, Pageable nextPage);

    @Query(nativeQuery = true, value = "select * from books b join book2genre bt on b.id=bt.book_id join genre g on bt.genre_id =g.id where g.slug=:slug")
    Page<Book> getBooksByGenreSlug(String slug, Pageable nextPage);

    @Query("SELECT b FROM Book b " +
            "LEFT JOIN Book2UserEntity bu ON b.id = bu.book.id " +
            "LEFT JOIN Book2UserTypeEntity but ON bu.userType.id = but.id " +
            "GROUP BY b.id " +
            "ORDER BY (COUNT(bu.user.id) + 0.7 * SUM(CASE WHEN but.code = 'CART' THEN 1 ELSE 0 END) + 0.4 * SUM(CASE WHEN but.code = 'KEPT' THEN 1 ELSE 0 END)) DESC")
    Page<Book> findAllByOrderByBookPopularityDesc(Pageable nexPage);

    @Query(nativeQuery = true,value = "SELECT books.* FROM books INNER JOIN book2author ON books.id = book2author.book_id INNER JOIN author ON book2author.author_id = author.id WHERE author.slug=:slug")
    Page<Book> findBooksByAuthorSlug(String slug, Pageable nexPage);

    Book findBookBySlug(String slug);

}

package com.example.MyBookShopApp.data;
import com.example.MyBookShopApp.data.book.file.BookFileTypeEntity;
import javax.persistence.*;


@Entity
@Table(name = "book_file")
public class BookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;
    @OneToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private BookFileTypeEntity bookFileTypeId;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String path;

    public BookFile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public BookFileTypeEntity getBookFileTypeId() {
        return bookFileTypeId;
    }

    public void setBookFileTypeId(BookFileTypeEntity bookFileTypeId) {
        this.bookFileTypeId = bookFileTypeId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
package com.librarysystem.dto;

import com.librarysystem.entity.BookEntity;
import lombok.Data;


@Data
public class BookDTO {
    private Long id;
    private String bookName;
    private String author;
    private String writtingDate;
    private Long libraryId;

    public BookDTO(Long id, String bookName, String author, String writtingDate, Long libraryId) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.writtingDate = writtingDate;
        this.libraryId = libraryId;
    }

    public static BookDTO toBookDTO(BookEntity book) {
        return new BookDTO(book.getId(), book.getBookName(), book.getAuthor(), book.getWrittingDate(), book.getLibrary().getId());
    }
}

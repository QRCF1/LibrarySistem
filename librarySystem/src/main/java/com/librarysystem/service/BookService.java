package com.librarysystem.service;

import com.librarysystem.dto.BookDTO;
import com.librarysystem.entity.BookEntity;
import com.librarysystem.entity.LibraryEntity;
import com.librarysystem.entity.ReaderEntity;
import com.librarysystem.repository.BookRepository;
import com.librarysystem.repository.LibraryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    public BookService(BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    public List<BookDTO> books() {
        return bookRepository.findAll().stream().map(BookDTO::toBookDTO).toList();
    }

    public ResponseEntity<BookDTO> book(Long bookId) {
        try {
            return ResponseEntity.ok().body(BookDTO.toBookDTO(bookRepository.findById(bookId).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<BookDTO> addBook(BookEntity book, Long libraryId) {
        try {
            LibraryEntity library = libraryRepository.findById(libraryId).orElseThrow();
            List<BookEntity> books = library.getBooks();
            books.add(book);
            book.setLibrary(library);
            library.setBooks(books);

            return ResponseEntity.ok().body(BookDTO.toBookDTO(bookRepository.save(book)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<BookDTO> updateBook(BookEntity changedBook, Long bookId) {
        try {
            BookEntity book = bookRepository.findById(bookId).orElseThrow();
            if(changedBook.getBookName() != null) {
                book.setBookName(changedBook.getBookName());
            }
            if(changedBook.getAuthor() != null) {
                book.setAuthor(changedBook.getAuthor());
            }
            if(changedBook.getWrittingDate() != null) {
                book.setWrittingDate(changedBook.getWrittingDate());
            }

            return ResponseEntity.ok().body(BookDTO.toBookDTO(bookRepository.save(book)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<BookDTO> removeBook(Long bookId) {
        try {
            BookEntity book = bookRepository.findById(bookId).orElseThrow();
            LibraryEntity library = book.getLibrary();

            library.getReaders().forEach(reader -> reader.getBooks().remove(book));
            library.getBooks().remove(book);

            BookDTO bookDTO = BookDTO.toBookDTO(book);
            bookRepository.delete(book);

            return ResponseEntity.ok().body(bookDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

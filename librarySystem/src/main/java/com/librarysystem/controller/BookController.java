package com.librarysystem.controller;

import com.librarysystem.dto.BookDTO;
import com.librarysystem.entity.BookEntity;
import com.librarysystem.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ResponseEntity<List<BookDTO>> books() {
        return ResponseEntity.ok().body(bookService.books());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> book(@PathVariable Long bookId) {
        return bookService.book(bookId);
    }

    @PostMapping("/{libraryId}")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookEntity book, @PathVariable Long libraryId) {
        return bookService.addBook(book, libraryId);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookEntity book, @PathVariable Long bookId) {
        return bookService.updateBook(book, bookId);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<BookDTO> removeBook(@PathVariable Long bookId) {
        return bookService.removeBook(bookId);
    }
}

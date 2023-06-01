package com.librarysystem.controller;

import com.librarysystem.dto.ReaderDTO;
import com.librarysystem.entity.ReaderEntity;
import com.librarysystem.service.ReaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    private final ReaderService readerService;

    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ReaderDTO>> readers() {
        return ResponseEntity.ok().body(readerService.readers());
    }

    @GetMapping("/{readerId}")
    public ResponseEntity<ReaderDTO> reader(@PathVariable Long readerId) {
        return readerService.reader(readerId);
    }

    @GetMapping("/avnumbooks")
    public ResponseEntity<String> averageNumberBooksOfReaders() {
        return readerService.averageNumberBooksOfReaders();
    }

    @PostMapping("/{readerId}/{bookId}")
    public ResponseEntity<ReaderDTO> addBook(@PathVariable Long readerId, @PathVariable Long bookId) {
        return readerService.addBook(readerId, bookId);
    }

    @PostMapping("/{libraryId}")
    public ResponseEntity<ReaderDTO> addReader(@RequestBody ReaderEntity reader, @PathVariable Long libraryId) {
        return readerService.addReader(reader, libraryId);
    }

    @PatchMapping("/{readerId}")
    public ResponseEntity<ReaderDTO> updateReader(@RequestBody ReaderEntity reader, @PathVariable Long readerId) {
        return readerService.updateReader(reader, readerId);
    }

    @DeleteMapping("/{readerId}")
    public ResponseEntity<ReaderDTO> removeReader(@PathVariable Long readerId) {
        return readerService.removeReader(readerId);
    }
}

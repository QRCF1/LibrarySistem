package com.librarysystem.controller;

import com.librarysystem.dto.LibraryDTO;
import com.librarysystem.entity.LibraryEntity;
import com.librarysystem.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public ResponseEntity<List<LibraryDTO>> libraries() {
        return ResponseEntity.ok().body(libraryService.libraries());
    }

    @GetMapping("/{libraryId}")
    public ResponseEntity<LibraryDTO> library(@PathVariable Long libraryId) {
        return libraryService.library(libraryId);
    }

    @PostMapping("/")
    public ResponseEntity<LibraryDTO> addLibrary(@RequestBody LibraryEntity library) {
        return ResponseEntity.ok().body(libraryService.addLibrary(library));
    }

    @PatchMapping("/{libraryId}")
    public ResponseEntity<LibraryDTO> updateLibrary(@RequestBody LibraryEntity library, @PathVariable Long libraryId) {
        return libraryService.updateLibrary(library, libraryId);
    }

    @DeleteMapping("/{libraryId}")
    public ResponseEntity<LibraryDTO> removeLibrary(@PathVariable Long libraryId) {
        return libraryService.removeLibrary(libraryId);
    }

}

package com.librarysystem.service;

import com.librarysystem.dto.LibraryDTO;
import com.librarysystem.entity.BookEntity;
import com.librarysystem.entity.LibraryEntity;
import com.librarysystem.entity.ReaderEntity;
import com.librarysystem.repository.BookRepository;
import com.librarysystem.repository.LibraryRepository;
import com.librarysystem.repository.ReaderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;

    public LibraryService(LibraryRepository libraryRepository, ReaderRepository readerRepository, BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.readerRepository = readerRepository;
        this.bookRepository = bookRepository;
    }

    public List<LibraryDTO> libraries() {
        List<LibraryEntity> libraries = libraryRepository.findAll();
        List<LibraryDTO> libraryDTOS = new ArrayList<>();
        libraries.forEach(library -> libraryDTOS.add(LibraryDTO.toLibraryDTO(library)));

        return libraryDTOS;
    }

    public ResponseEntity<LibraryDTO> library(Long libraryId) {
        try {
            return ResponseEntity.ok().body(LibraryDTO.toLibraryDTO(libraryRepository.findById(libraryId).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public LibraryDTO addLibrary(LibraryEntity library) {
        return LibraryDTO.toLibraryDTO(libraryRepository.save(library));
    }

    public ResponseEntity<LibraryDTO> updateLibrary(LibraryEntity changedLibrary, Long libraryId) {
        try {
            LibraryEntity library = libraryRepository.findById(libraryId).orElseThrow();
            if(changedLibrary.getName() != null) {
                library.setName(changedLibrary.getName());
            }
            if(changedLibrary.getAddress() != null) {
                library.setAddress(changedLibrary.getAddress());
            }
            return ResponseEntity.ok().body(LibraryDTO.toLibraryDTO(libraryRepository.save(library)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<LibraryDTO> removeLibrary(Long libraryId) {
        try {
            LibraryEntity library = libraryRepository.findById(libraryId).orElseThrow();

            List<ReaderEntity> readers = library.getReaders();
            List<BookEntity> books = library.getBooks();

            readers.forEach(reader -> reader.getBooks().remove(books));
            readerRepository.deleteAll(readers);

            libraryRepository.deleteById(library.getId());

            return ResponseEntity.ok().body(LibraryDTO.toLibraryDTO(library));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}

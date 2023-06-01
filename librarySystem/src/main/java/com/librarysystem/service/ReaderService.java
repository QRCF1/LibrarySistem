package com.librarysystem.service;

import com.librarysystem.dto.ReaderDTO;
import com.librarysystem.entity.BookEntity;
import com.librarysystem.entity.LibraryEntity;
import com.librarysystem.entity.ReaderEntity;
import com.librarysystem.repository.BookRepository;
import com.librarysystem.repository.LibraryRepository;
import com.librarysystem.repository.ReaderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;

    public ReaderService(ReaderRepository readerRepository, LibraryRepository libraryRepository, BookRepository bookRepository) {
        this.readerRepository = readerRepository;
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    public List<ReaderDTO> readers() {
        List<ReaderEntity> readers = readerRepository.findAll();
        List<ReaderDTO> readerDTOS = new ArrayList<>();
        readers.forEach(reader -> readerDTOS.add(ReaderDTO.toReaderDTO(reader)));

        return readerDTOS;
    }

    public ResponseEntity<ReaderDTO> reader(Long readerId) {
        try {
            return ResponseEntity.ok().body(ReaderDTO.toReaderDTO(readerRepository.findById(readerId).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<ReaderDTO> addReader(ReaderEntity reader, Long libraryId) throws EntityNotFoundException {
        Optional<LibraryEntity> optLib = libraryRepository.findById(libraryId);
        if (optLib.isPresent()) {
            LibraryEntity library = optLib.get();
            List<ReaderEntity> readers = library.getReaders();
            readers.add(reader);
            reader.setLibrary(library);
            library.setReaders(readers);

            return ResponseEntity.ok().body(ReaderDTO.toReaderDTO(readerRepository.save(reader)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<ReaderDTO> addBook(Long readerId, Long bookId) {
        try {
            BookEntity book = bookRepository.findById(bookId).orElseThrow();
            ReaderEntity reader = readerRepository.findById(readerId).orElseThrow();

            reader.getBooks().add(book);

            return ResponseEntity.ok().body(ReaderDTO.toReaderDTO(readerRepository.save(reader)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<ReaderDTO> updateReader(ReaderEntity changedReader, Long readerId) {
        Optional<ReaderEntity> optReader = readerRepository.findById(readerId);
        if (optReader.isPresent()) {
            ReaderEntity reader = optReader.get();
            if (changedReader.getFio() != null) {
                reader.setFio(changedReader.getFio());
            }
            if (changedReader.getAddress() != null) {
                reader.setAddress(changedReader.getAddress());
            }
            if (changedReader.getPhoneNumber() != null) {
                reader.setPhoneNumber(changedReader.getPhoneNumber());
            }
            return ResponseEntity.ok().body(ReaderDTO.toReaderDTO(readerRepository.save(reader)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<String> averageNumberBooksOfReaders() {
        List<ReaderEntity> readers = readerRepository.findAll();
        if(readers.isEmpty()) {
            return ResponseEntity.badRequest().body("Читателей нет!");
        } else {
            double avNumBooks = (double) readers.stream().map(ReaderEntity::getBooks).mapToInt(List::size).sum() / readers.size();
            return ResponseEntity.ok().body("Среднее количество книг у читателей = " + avNumBooks);
        }
    }

    public ResponseEntity<ReaderDTO> removeReader(Long readerId) {
        try {
            ReaderEntity reader = readerRepository.findById(readerId).orElseThrow();

            LibraryEntity library = reader.getLibrary();
            reader.getBooks().clear();
            library.getReaders().remove(reader);

            ReaderDTO readerDTO = ReaderDTO.toReaderDTO(reader);
            readerRepository.delete(reader);

            return ResponseEntity.ok().body(readerDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

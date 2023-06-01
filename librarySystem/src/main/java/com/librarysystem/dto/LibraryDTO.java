package com.librarysystem.dto;

import com.librarysystem.entity.BookEntity;
import com.librarysystem.entity.LibraryEntity;
import com.librarysystem.entity.ReaderEntity;
import lombok.Data;

import java.util.List;

@Data
public class LibraryDTO {
    private Long id;
    private String name;
    private String address;
    private List<Long> readersId;
    private List<Long> booksId;

    public LibraryDTO(Long id, String name, String address, List<Long> readersId, List<Long> booksId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.readersId = readersId;
        this.booksId = booksId;
    }

    public static LibraryDTO toLibraryDTO(LibraryEntity library) {
        return new LibraryDTO(library.getId(), library.getName(), library.getAddress(), library.getReaders().stream()
                .map(ReaderEntity::getId).toList(), library.getBooks().stream().map(BookEntity::getId).toList());
    }
}

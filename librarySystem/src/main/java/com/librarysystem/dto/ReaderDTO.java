package com.librarysystem.dto;

import com.librarysystem.entity.BookEntity;
import com.librarysystem.entity.ReaderEntity;
import lombok.Data;

import java.util.List;

@Data
public class ReaderDTO {
    private Long id;
    private String fio;
    private String phoneNumber;
    private Long library;
    private List<Long> booksId;

    public ReaderDTO(Long id, String fio, String phoneNumber, Long library, List<Long> booksId) {
        this.id = id;
        this.fio = fio;
        this.phoneNumber = phoneNumber;
        this.library = library;
        this.booksId = booksId;
    }
    
    public static ReaderDTO toReaderDTO(ReaderEntity reader) {
        return new ReaderDTO(reader.getId(), reader.getFio(), reader.getPhoneNumber(), reader.getLibrary().getId(), reader.getBooks().stream().map(BookEntity::getId).toList());
    }
}

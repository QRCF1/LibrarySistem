package com.librarysystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "readers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phoneNumber")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fio;
    private String address;
    private String phoneNumber;

    @ManyToOne
    private LibraryEntity library;
    @JoinTable(
            name = "reader_book",
            joinColumns = {@JoinColumn(name = "reader_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<BookEntity> books = new ArrayList<>();
}

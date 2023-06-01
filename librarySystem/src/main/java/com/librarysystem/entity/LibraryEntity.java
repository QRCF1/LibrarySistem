package com.librarysystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libraries", uniqueConstraints = {
        @UniqueConstraint(columnNames = "address"),
        @UniqueConstraint(columnNames = "name"),
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ReaderEntity> readers = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<BookEntity> books = new ArrayList<>();
}

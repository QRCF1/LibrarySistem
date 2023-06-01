package com.librarysystem.repository;

import com.librarysystem.entity.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<ReaderEntity, Long> {
    Optional<ReaderEntity> findById(Long id);
}

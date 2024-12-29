package com.mybackend.repository;

import com.mybackend.model.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    List<Archive> findAllByOrderByCreatedOnDesc();
}

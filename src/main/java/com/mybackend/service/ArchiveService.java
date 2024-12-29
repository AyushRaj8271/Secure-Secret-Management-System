package com.mybackend.service;

import com.mybackend.dto.ArchiveDTO;
import com.mybackend.model.Secret;

import java.util.List;
import java.util.Optional;

public interface ArchiveService {
    Optional<ArchiveDTO> findById(Long id);
    List<ArchiveDTO> findAll();
    ArchiveDTO save(ArchiveDTO archiveEntityDTO);
    void deleteOldestIfMaxReached();
    void archiveSecret(Secret secretToArchive);
}


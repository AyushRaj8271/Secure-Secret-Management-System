package com.mybackend.service.impl;

import com.mybackend.dto.ArchiveDTO;
import com.mybackend.mapper.ArchiveMapper;
import com.mybackend.model.Archive;
import com.mybackend.model.Secret;
import com.mybackend.repository.ArchiveRepository;
import com.mybackend.service.ArchiveService;
import com.mybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArchiveRepository archiveRepository;

    @Autowired
    private UserService userService;


    @Override
    public Optional<ArchiveDTO> findById(Long id) {
        Optional<Archive> archiveEntity = archiveRepository.findById(id);
        return archiveEntity.map(ArchiveMapper::toDTO);
    }

    @Override
    public List<ArchiveDTO> findAll() {
        List<Archive> archiveEntities = archiveRepository.findAllByOrderByCreatedOnDesc();
        return archiveEntities.stream()
                .map(ArchiveMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ArchiveDTO save(ArchiveDTO archiveEntityDTO) {
        deleteOldestIfMaxReached();
        Archive archiveEntity = ArchiveMapper.toEntity(archiveEntityDTO, userService.findById(archiveEntityDTO.getCreatedBy()).orElseThrow(() -> new RuntimeException("User not found")));
        Archive savedEntity = archiveRepository.save(archiveEntity);
        return ArchiveMapper.toDTO(savedEntity);
    }

    @Override
    public void deleteOldestIfMaxReached() {
        List<Archive> archives = archiveRepository.findAllByOrderByCreatedOnDesc();
        if (archives.size() >= 10) {
            Archive oldest = archives.get(archives.size() - 1);
            archiveRepository.delete(oldest);
        }
    }

    @Override
    public void archiveSecret(Secret secretToArchive) {
        ArchiveDTO archiveDTO = new ArchiveDTO();
        // New archive entry
        archiveDTO.setName(secretToArchive.getName());
        archiveDTO.setDescription(secretToArchive.getDescription());
        archiveDTO.setCreatedBy(secretToArchive.getCreatedBy().getUserId());
        archiveDTO.setCreatedOn(secretToArchive.getCreatedOn());
        archiveDTO.setLastModified(secretToArchive.getLastModified());
        archiveDTO.setEncryptionVersion(secretToArchive.getEncryptionVersion());
        archiveDTO.setAccessCount(secretToArchive.getAccessCount());
        archiveDTO.setPath(secretToArchive.getPath());
        archiveDTO.setEncryptedData(secretToArchive.getEncryptedData());

        // Save the archive
        save(archiveDTO);
    }
}

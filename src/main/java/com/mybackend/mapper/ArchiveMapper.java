package com.mybackend.mapper;

import com.mybackend.dto.ArchiveDTO;
import com.mybackend.model.Archive;
import com.mybackend.model.User;

public class ArchiveMapper {

    public static Archive toEntity(ArchiveDTO dto, User createdBy) {
        Archive archive = new Archive();
        archive.setArchiveId(dto.getArchiveId());
        archive.setName(dto.getName());
        archive.setDescription(dto.getDescription());
        archive.setCreatedBy(createdBy);
        archive.setCreatedOn(dto.getCreatedOn());
        archive.setLastModified(dto.getLastModified());
        archive.setEncryptionVersion(dto.getEncryptionVersion());
        archive.setAccessCount(dto.getAccessCount());
        archive.setPath(dto.getPath());
        archive.setEncryptedData(dto.getEncryptedData());
        return archive;
    }

    public static ArchiveDTO toDTO(Archive archive) {
        ArchiveDTO dto = new ArchiveDTO();
        dto.setArchiveId(archive.getArchiveId());
        dto.setName(archive.getName());
        dto.setDescription(archive.getDescription());
        dto.setCreatedBy(archive.getCreatedBy().getUserId());
        dto.setCreatedOn(archive.getCreatedOn());
        dto.setLastModified(archive.getLastModified());
        dto.setEncryptionVersion(archive.getEncryptionVersion());
        dto.setAccessCount(archive.getAccessCount());
        dto.setPath(archive.getPath());
        dto.setEncryptedData(archive.getEncryptedData());
        return dto;
    }
}

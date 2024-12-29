package com.mybackend.mapper;

import com.mybackend.dto.SecretDTO;
import com.mybackend.model.Secret;

public class SecretMapper {

    public static SecretDTO toDTO(Secret secret) {
        SecretDTO secretDTO = new SecretDTO();
        secretDTO.setSecretId(secret.getSecretId());
        secretDTO.setName(secret.getName());
        secretDTO.setDescription(secret.getDescription());
        secretDTO.setCreatedBy(secret.getCreatedBy().getUserId()); // Assuming createdBy is a User object
        secretDTO.setCreatedOn(secret.getCreatedOn());
        secretDTO.setLastModified(secret.getLastModified());
        secretDTO.setEncryptionVersion(secret.getEncryptionVersion());
        secretDTO.setAccessCount(secret.getAccessCount());
        secretDTO.setPath(secret.getPath());
        secretDTO.setEncryptedData(secret.getEncryptedData());
        return secretDTO;
    }

    public static Secret toEntity(SecretDTO secretDTO) {
        Secret secret = new Secret();
        secret.setSecretId(secretDTO.getSecretId());
        secret.setName(secretDTO.getName());
        secret.setDescription(secretDTO.getDescription());
        // Assuming you will set createdBy using a User object fetched from the database
        secret.setCreatedOn(secretDTO.getCreatedOn());
        secret.setLastModified(secretDTO.getLastModified());
        secret.setEncryptionVersion(secretDTO.getEncryptionVersion());
        secret.setAccessCount(secretDTO.getAccessCount());
        secret.setPath(secretDTO.getPath());
        secret.setEncryptedData(secretDTO.getEncryptedData());
        return secret;
    }
}

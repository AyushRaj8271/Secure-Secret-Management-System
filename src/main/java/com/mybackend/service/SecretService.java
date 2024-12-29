package com.mybackend.service;

import com.mybackend.model.Secret;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SecretService {

    Secret saveSecret(Secret secret);

    Optional<Secret> findSecretById(Long id);

    List<Secret> findAllSecrets();

    void deleteSecretById(Long id);


    Page<Secret> getSecretsWithPagination(Pageable pageable);


    void rotateSecret(Long id); // New method for rotating a secret
    void rotateAllSecrets(); // New method for rotating all secrets


}

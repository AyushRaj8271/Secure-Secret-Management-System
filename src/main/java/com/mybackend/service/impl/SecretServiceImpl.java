package com.mybackend.service.impl;

import com.mybackend.model.Secret;
import com.mybackend.repository.SecretRepository;
import com.mybackend.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.mybackend.util.EncryptionUtil;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the SecretService interface.
 * Provides methods for managing secrets, including saving, finding, and deleting secrets.
 * Also handles encryption and decryption of secret data.
 */

@Service
public class SecretServiceImpl implements SecretService {

    @Autowired
    private SecretRepository secretRepository;

    /**
     * Saves a secret after encrypting its data.
     *
     * @param secret the secret to save
     * @return the saved secret
     * @throws RuntimeException if an error occurs during encryption
     */

    @Override
    public Secret saveSecret(Secret secret) {
        try {
            secret.setEncryptedData(EncryptionUtil.encrypt(secret.getEncryptedData()));
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
        return secretRepository.save(secret);
        //return secretRepository.save(secret);
    }

    /**
     * Finds a secret by its ID and decrypts its data.
     *
     * @param id the ID of the secret to find
     * @return an Optional containing the found secret, or an empty Optional if no secret is found
     * @throws RuntimeException if an error occurs during decryption
     */
    @Override
    public Optional<Secret> findSecretById(Long id) {
        Optional<Secret> secret = secretRepository.findById(id);
        secret.ifPresent(s -> {
            try {
                s.setEncryptedData(EncryptionUtil.decrypt(s.getEncryptedData()));
            } catch (Exception e) {
                throw new RuntimeException("Error decrypting data", e);
            }
        });
        return secret;
        //return secretRepository.findById(id);
    }

    /**
     * Finds all secrets.
     *
     * @return a list of all secrets
     */
    @Override
    public List<Secret> findAllSecrets() {
//        List<Secret> secrets = secretRepository.findAll();
//        secrets.forEach(s -> {
//            try {
//                s.setEncryptedData(EncryptionUtil.decrypt(s.getEncryptedData()));
//            } catch (Exception e) {
//                throw new RuntimeException("Error decrypting data", e);
//            }
//        });
//        return secrets;
        return secretRepository.findAll();
    }

    /**
     * Deletes a secret by its ID.
     *
     * @param id the ID of the secret to delete
     */

    @Override
    public void deleteSecretById(Long id) {
        secretRepository.deleteById(id);
    }

    /**
     * Retrieves secrets with pagination.
     *
     * @param pageable the pagination information
     * @return a page of secrets
     */

    @Override
    public Page<Secret> getSecretsWithPagination(Pageable pageable) {
        return secretRepository.findAll(pageable);
    }


    @Override
    public void rotateSecret(Long id) {
        try {
            Optional<Secret> secretOpt = secretRepository.findById(id);

            if (secretOpt.isPresent()) {
                Secret secret = secretOpt.get();

                // Encrypt the data
                String newEncryptedData = EncryptionUtil.encrypt(secret.getEncryptedData());

                // Set the new encrypted data
                secret.setEncryptedData(newEncryptedData);

                // Save the updated secret
                saveSecret(secret);
            } else {
                throw new RuntimeException("Secret not found with id: " + id);
            }
        } catch (Exception e) {
            // Log the exception (use a logging framework in real applications)
            System.err.println("Error rotating secret: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error rotating secret: " + e.getMessage());
        }
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void rotateAllSecrets() {
        List<Secret> secrets = findAllSecrets();
        for (Secret secret : secrets) {
            rotateSecret(secret.getSecretId());
        }
    }





}

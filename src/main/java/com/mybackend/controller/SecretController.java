package com.mybackend.controller;

import com.mybackend.dto.ArchiveDTO;
import com.mybackend.dto.SecretDTO;
import com.mybackend.mapper.SecretMapper;
import com.mybackend.model.Secret;
import com.mybackend.model.User;
import com.mybackend.service.SecretService;
import com.mybackend.service.UserService;
import com.mybackend.util.EncryptionUtil;
import com.mybackend.util.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mybackend.service.ArchiveService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing secrets.
 */

@RestController
@RequestMapping("/api/secrets")
public class SecretController {




    @Autowired
    private ArchiveService archiveService; // Properly injected

    @Autowired
    private SecretService secretService;

    /**
     * Creates a new secret.
     *
     * @param secretDTO the secret DTO
     * @return the response entity with the created secret
     */

    @Autowired
    private UserService userService;

    private RateLimiter rateLimiter = new RateLimiter();

    /**
     * Creates a new secret.
     *
     * @param secretDTO the secret DTO
     * @return the response entity with the created secret
     */
    @PostMapping("/new")
    public ResponseEntity<?> createSecret(@RequestBody SecretDTO secretDTO) {
        try {
            Secret secret = SecretMapper.toEntity(secretDTO);
            // Assuming you have a method to fetch User by ID and set it to createdBy
            // secret.setCreatedBy(userService.findById(secretDTO.getCreatedBy()).orElseThrow(() -> new RuntimeException("User not found")));

            // Fetch the user by ID and set it to createdBy
            User createdBy = userService.findById(secretDTO.getCreatedBy())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            secret.setCreatedBy(createdBy);

            Secret savedSecret = secretService.saveSecret(secret);
            SecretDTO savedSecretDTO = SecretMapper.toDTO(savedSecret);
            return ResponseEntity.ok(savedSecretDTO);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error creating secret: " + e.getMessage());
        }
    }

    /**
     * Retrieves a secret by its ID.
     *
     * @param id the ID of the secret
     * @return the response entity with the found secret
     */

    @GetMapping("/{id}")
    public ResponseEntity getSecretById(@PathVariable Long id, @RequestHeader("userId") String userId) {
        if (!rateLimiter.isRequestAllowed(userId)) {
            return ResponseEntity.status(429).body("Rate limit exceeded");
        }

        try {
            Optional<Secret> secret = secretService.findSecretById(id);
            return secret.map(s -> ResponseEntity.ok(SecretMapper.toDTO(s)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving secret: " + e.getMessage());
        }
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getSecretById(@PathVariable Long id) {
//        try {
//            Optional<Secret> secret = secretService.findSecretById(id);
//            return secret.map(s -> ResponseEntity.ok(SecretMapper.toDTO(s)))
//                    .orElseGet(() -> ResponseEntity.notFound().build());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error retrieving secret: " + e.getMessage());
//        }
//    }

//    @GetMapping
//    public ResponseEntity<?> getAllSecrets() {
//        try {
//            List<Secret> secrets = secretService.findAllSecrets();
//            List<SecretDTO> secretDTOs = secrets.stream()
//                    .map(SecretMapper::toDTO)
//                    .collect(Collectors.toList());
//            return ResponseEntity.ok(secretDTOs);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error retrieving secrets: " + e.getMessage());
//        }
//    }
    /**
     * Retrieves all secrets.
     *
     * @return the response entity with the list of secrets
     */
@GetMapping
public ResponseEntity<?> getAllSecrets() {
    try {
        List<Secret> secrets = secretService.findAllSecrets();
        List<SecretDTO> secretDTOs = secrets.stream()
                .map(SecretMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(secretDTOs);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error retrieving secrets: " + e.getMessage());
    }
}

    /**
     * Updates a secret by its ID.
     *
     * @param id the ID of the secret
     * @param secretDTO the secret DTO
     * @return the response entity with the updated secret
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSecret(@PathVariable Long id, @RequestBody SecretDTO secretDTO) {
        try {
            Optional<Secret> existingSecret = secretService.findSecretById(id);
            if (existingSecret.isPresent()) {
                // Archive the existing secret
//                Secret secretToArchive = existingSecret.get();
//                ArchiveDTO archiveDTO = new ArchiveDTO();
//                archiveDTO.setArchiveId(null); // New archive entry
//                archiveDTO.setName(secretToArchive.getName());
//                archiveDTO.setDescription(secretToArchive.getDescription());
//                archiveDTO.setCreatedBy(secretToArchive.getCreatedBy().getUserId());
//                archiveDTO.setCreatedOn(secretToArchive.getCreatedOn());
//                archiveDTO.setLastModified(secretToArchive.getLastModified());
//                archiveDTO.setEncryptionVersion(secretToArchive.getEncryptionVersion());
//                archiveDTO.setAccessCount(secretToArchive.getAccessCount());
//                archiveDTO.setPath(secretToArchive.getPath());
//                archiveDTO.setEncryptedData(secretToArchive.getEncryptedData());
//                  archiveService.save(archiveDTO);
                // Archive the existing secret
                Secret secretToArchive = existingSecret.get();
                archiveService.archiveSecret(secretToArchive);




                // Save the archive
                
                Secret secret = SecretMapper.toEntity(secretDTO);
                secret.setSecretId(id); // Ensure the ID is set to the path variable
                // Assuming you have a method to fetch User by ID and set it to createdBy
                // secret.setCreatedBy(userService.findById(secretDTO.getCreatedBy()).orElseThrow(() -> new RuntimeException("User not found")));

                User createdBy = userService.findById(secretDTO.getCreatedBy())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                secret.setCreatedBy(createdBy);

                // v1 -> v2 logic

                // Encrypt the sensitive data
                secret.setEncryptedData(EncryptionUtil.encrypt(secret.getEncryptedData()));

                // Handle version increment
                String currentVersion = existingSecret.get().getEncryptionVersion();
                String newVersion = incrementVersion(currentVersion);
                secret.setEncryptionVersion(newVersion);





                Secret updatedSecret = secretService.saveSecret(secret);
                SecretDTO updatedSecretDTO = SecretMapper.toDTO(updatedSecret);
                return ResponseEntity.ok(updatedSecretDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating secret: " + e.getMessage());
        }
    }

    private String incrementVersion(String version) {
        int versionNumber = Integer.parseInt(version.substring(1));
        versionNumber++;
        return "v" + versionNumber;
    }

    /**
     * Deletes a secret by its ID.
     *
     * @param id the ID of the secret
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSecretById(@PathVariable Long id) {
        try {
            secretService.deleteSecretById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting secret: " + e.getMessage());
        }
    }

    /**
     * Retrieves secrets with pagination.
     *
     * @param page the page number
     * @param size the page size
     * @param sortBy the field to sort by
     * @param sortDir the sort direction
     * @return the response entity with the page of secrets
     */
    @GetMapping("/pageing")
    public ResponseEntity<?> getAllSecretsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        try {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Secret> secretPage = secretService.getSecretsWithPagination(pageable);

            return ResponseEntity.ok(secretPage);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error retrieving secrets: " + e.getMessage());
        }
    }


    @PostMapping("/rotate/{id}")
    public ResponseEntity rotateSecret(@PathVariable Long id) {
        try {
            secretService.rotateSecret(id);
            return ResponseEntity.ok("Secret rotated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error rotating secret: " + e.getMessage());
        }
    }

    @PostMapping("/rotate-all")
    public ResponseEntity rotateAllSecrets() {
        try {
            secretService.rotateAllSecrets();
            return ResponseEntity.ok("All secrets rotated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error rotating secrets: " + e.getMessage());
        }
    }



}


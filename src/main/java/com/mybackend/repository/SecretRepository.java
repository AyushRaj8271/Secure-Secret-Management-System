package com.mybackend.repository;

import com.mybackend.model.Secret;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface SecretRepository extends JpaRepository<Secret, Long> {

    Optional<Secret> findByName(String name);

    @Query("SELECT COUNT(s) FROM Secret s")
    long countEncryptedSecrets();


}

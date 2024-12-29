package com.mybackend.impl;

import com.mybackend.model.Secret;
import com.mybackend.repository.SecretRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.mybackend.service.impl.SecretServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecretServiceImplTest {

    @Mock
    private SecretRepository secretRepository;

    @InjectMocks
    private SecretServiceImpl secretService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSecret() {
        Secret secret = new Secret();
        secret.setEncryptedData("testData");

        when(secretRepository.save(any(Secret.class))).thenReturn(secret);

        Secret result = secretService.saveSecret(secret);

        assertNotNull(result);
        verify(secretRepository, times(1)).save(secret);
    }

    @Test
    void testFindSecretById() {
        Secret secret = new Secret();
        secret.setSecretId(1L);
        secret.setEncryptedData("testData");

        when(secretRepository.findById(1L)).thenReturn(Optional.of(secret));

        Optional<Secret> result = secretService.findSecretById(1L);

        assertTrue(result.isPresent());
        verify(secretRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAllSecrets() {
        Secret secret1 = new Secret();
        Secret secret2 = new Secret();

        List<Secret> secrets = Arrays.asList(secret1, secret2);

        when(secretRepository.findAll()).thenReturn(secrets);

        List<Secret> result = secretService.findAllSecrets();

        assertEquals(2, result.size());
        verify(secretRepository, times(1)).findAll();
    }

    @Test
    void testDeleteSecretById() {
        secretService.deleteSecretById(1L);
        verify(secretRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetSecretsWithPagination() {
        Secret secret1 = new Secret();
        Secret secret2 = new Secret();

        List<Secret> secrets = Arrays.asList(secret1, secret2);
        Page<Secret> secretPage = new PageImpl<>(secrets);

        Pageable pageable = PageRequest.of(0, 10);
        when(secretRepository.findAll(pageable)).thenReturn(secretPage);

        Page<Secret> result = secretService.getSecretsWithPagination(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        verify(secretRepository, times(1)).findAll(pageable);
    }
}

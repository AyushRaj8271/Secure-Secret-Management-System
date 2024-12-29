package com.mybackend.controller;

import com.mybackend.dto.SecretDTO;
import com.mybackend.model.Secret;
import com.mybackend.model.User;
import com.mybackend.service.SecretService;
import com.mybackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecretController.class)
class SecretControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecretService secretService;

    @MockBean
    private UserService userService;

    @Test
    void testCreateSecret() throws Exception {
        SecretDTO secretDTO = new SecretDTO();
        secretDTO.setCreatedBy(1L);
        secretDTO.setEncryptedData("testData");

        Secret secret = new Secret();
        secret.setEncryptedData("testData");

        User user = new User();
        user.setUserId(1L);

        when(userService.findById(1L)).thenReturn(Optional.of(user));
        when(secretService.saveSecret(any(Secret.class))).thenReturn(secret);

        mockMvc.perform(post("/api/secrets/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"createdBy\":1,\"encryptedData\":\"testData\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetSecretById() throws Exception {
        Secret secret = new Secret();
        secret.setSecretId(1L);
        secret.setEncryptedData("testData");

        when(secretService.findSecretById(1L)).thenReturn(Optional.of(secret));

        mockMvc.perform(get("/api/secrets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.encryptedData").value("testData"));
    }

    @Test
    void testGetAllSecrets() throws Exception {
        Secret secret1 = new Secret();
        Secret secret2 = new Secret();

        when(secretService.findAllSecrets()).thenReturn(Arrays.asList(secret1, secret2));

        mockMvc.perform(get("/api/secrets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateSecret() throws Exception {
        SecretDTO secretDTO = new SecretDTO();
        secretDTO.setCreatedBy(1L);
        secretDTO.setEncryptedData("updatedData");

        Secret secret = new Secret();
        secret.setSecretId(1L);
        secret.setEncryptedData("updatedData");

        User user = new User();
        user.setUserId(1L);

        when(secretService.findSecretById(1L)).thenReturn(Optional.of(secret));
        when(userService.findById(1L)).thenReturn(Optional.of(user));
        when(secretService.saveSecret(any(Secret.class))).thenReturn(secret);

        mockMvc.perform(put("/api/secrets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"createdBy\":1,\"encryptedData\":\"updatedData\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.encryptedData").value("updatedData"));
    }

    @Test
    void testDeleteSecretById() throws Exception {
        mockMvc.perform(delete("/api/secrets/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllSecretsWithPagination() throws Exception {
        mockMvc.perform(get("/api/secrets/paging")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "createdOn")
                        .param("sortDir", "desc"))
                .andExpect(status().isOk());
    }
}

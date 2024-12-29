package com.mybackend.controller;

import com.mybackend.dto.UserDTO;
import com.mybackend.mapper.UserMapper;
import com.mybackend.model.User;
import com.mybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
//        User user = UserMapper.toEntity(userDTO);
//        User savedUser = userService.saveUser(user);
//        UserDTO savedUserDTO = UserMapper.toDTO(savedUser);
//        return ResponseEntity.ok(savedUserDTO);

        try {
            User user = UserMapper.toEntity(userDTO);
            User savedUser = userService.saveUser(user);
            UserDTO savedUserDTO = UserMapper.toDTO(savedUser);
            return ResponseEntity.ok(savedUserDTO);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error creating user: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUserById(id);
        return user.map(u -> ResponseEntity.ok(UserMapper.toDTO(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(u -> ResponseEntity.ok(UserMapper.toDTO(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserDTO> userDTOs = users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            Optional<User> existingUser = userService.findUserById(id);
            if (existingUser.isPresent()) {
                User user = UserMapper.toEntity(userDTO);
                user.setUserId(id); // Ensure the ID is set to the path variable

                User updatedUser = userService.saveUser(user);
                UserDTO updatedUserDTO = UserMapper.toDTO(updatedUser);
                return ResponseEntity.ok(updatedUserDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating user: " + e.getMessage());
        }
    }
}

package com.mybackend.service;

import com.mybackend.model.User;
import java.util.Optional;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);
    List<User> findAllUsers();
    void deleteUserById(Long id);
    //for secret
    Optional<User> findById(Long id);
}

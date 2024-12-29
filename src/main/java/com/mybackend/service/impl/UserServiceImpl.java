package com.mybackend.service.impl;

import com.mybackend.model.User;
import com.mybackend.repository.UserRepository;
import com.mybackend.service.UserService;
import com.mybackend.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        try {
            user.setPassword(EncryptionUtil.encrypt(user.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
        return userRepository.save(user);
        //return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}

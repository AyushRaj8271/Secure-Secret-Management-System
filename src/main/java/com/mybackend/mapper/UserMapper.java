package com.mybackend.mapper;

import com.mybackend.dto.UserDTO;
import com.mybackend.model.User;
import com.mybackend.util.EncryptionUtil;

public class UserMapper {

    public static UserDTO toDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO){
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        user.setPassword(userDTO.getPassword());
//        try {
//            user.setPassword(EncryptionUtil.encrypt(userDTO.getPassword()));
//        } catch (Exception e) {
//            throw new RuntimeException("Error encrypting password", e);
//        }

        return user;
    }


}

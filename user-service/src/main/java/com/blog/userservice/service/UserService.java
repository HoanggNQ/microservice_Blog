package com.blog.userservice.service;

import com.blog.userservice.model.dto.UserDTO;

public interface UserService {
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
}

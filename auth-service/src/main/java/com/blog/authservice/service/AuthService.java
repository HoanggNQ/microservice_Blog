package com.blog.authservice.service;

import com.blog.authservice.model.dto.JwtResponse;
import com.blog.authservice.model.dto.LoginRequest;
import com.blog.authservice.model.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    JwtResponse authenticateUser(LoginRequest loginDto);

}

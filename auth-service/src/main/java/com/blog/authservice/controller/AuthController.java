package com.blog.authservice.controller;

import com.blog.authservice.model.dto.JwtResponse;
import com.blog.authservice.model.dto.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.blog.authservice.model.dto.LoginRequest;
import com.blog.authservice.model.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.authservice.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        System.out.println("Gọi vào register");
        authService.register(request);
        return ResponseEntity.ok("Đăng ký thành công");
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody @Valid LoginRequest request) {
        JwtResponse jwtResponse = authService.authenticateUser(request);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .code("AUTH_SUCCESS")
                        .message("Welcome To School Medical System")
                        .status(HttpStatus.OK)
                        .isSuccess(true)
                        .data(jwtResponse)
                        .build()
        );

    }
}
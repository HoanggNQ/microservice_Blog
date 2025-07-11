package com.blog.authservice.service.impl;

import com.blog.authservice.model.dto.JwtResponse;
import com.blog.authservice.model.exception.ActionFailedException;
import com.blog.authservice.model.exception.AuthFailedException;
import com.blog.authservice.security.JwtService;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.blog.authservice.model.dto.LoginRequest;
import com.blog.authservice.model.dto.RegisterRequest;
import com.blog.authservice.model.entity.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.blog.authservice.repository.UserRepository;
import com.blog.authservice.service.AuthService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .roleName("USER") // Default role, can be changed later
                .build();

        userRepository.save(user);
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            UserEntity userEntity = userRepository.findByUsername(loginDto.getUsername())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            String token = jwtService.generateToken(authentication);
            Long principalId =  userEntity.getUserId();


            JwtResponse.UserInfo userInfo = new JwtResponse.UserInfo(
                    principalId,
                    userEntity.getUsername(),
                    userEntity.getEmail(),
                    userEntity.getRoleName()
            );

            return new JwtResponse(
                    token,
                    86400000 / 1000,
                    userInfo
            );
        } catch (BadCredentialsException e) {
            throw new AuthFailedException("Invalid email or password");
        } catch (NotFoundException | ActionFailedException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthFailedException("Authentication failed: " + e.getMessage());
        }
    }
}

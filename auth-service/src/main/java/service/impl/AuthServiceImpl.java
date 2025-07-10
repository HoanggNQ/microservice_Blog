package service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import model.dto.LoginRequest;
import model.dto.RegisterRequest;
import model.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import security.JwtService;
import service.AuthService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


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
                .build();

        userRepository.save(user);
    }

    @Override
    public String login(LoginRequest request) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
            throw new RuntimeException("Thông tin đăng nhập không hợp lệ");
        }

        return jwtService.generateToken(userOpt.get().getUsername());
    }
}

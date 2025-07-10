package service;

import model.dto.LoginRequest;
import model.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);

    String login(LoginRequest request);
}

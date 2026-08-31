package com.travelgo.auth.service;

import com.travelgo.auth.dto.LoginRequest;
import com.travelgo.auth.dto.LoginResponse;
import com.travelgo.auth.dto.RefreshTokenRequest;
import com.travelgo.auth.dto.RefreshTokenResponse;
import com.travelgo.auth.dto.RegisterRequest;

public interface AuthService {
    String register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
    String logout(String refreshToken);
}

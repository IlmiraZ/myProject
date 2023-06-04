package ru.ilmira.myProject.service;


import org.springframework.stereotype.Service;
import ru.ilmira.myProject.payload.request.LoginRequest;
import ru.ilmira.myProject.payload.request.SignupRequest;
import ru.ilmira.myProject.payload.request.TokenRefreshRequest;
import ru.ilmira.myProject.payload.response.JwtResponse;
import ru.ilmira.myProject.payload.response.MessageResponse;
import ru.ilmira.myProject.payload.response.TokenRefreshResponse;
@Service
public interface AuthService {

    JwtResponse signIn(LoginRequest loginRequest);

    MessageResponse signUp(SignupRequest signUpRequest);

    TokenRefreshResponse refreshToken(TokenRefreshRequest request);

    MessageResponse logout(String username);
}

package ru.ilmira.myProject.auth;


import ru.ilmira.myProject.payload.request.LoginRequest;
import ru.ilmira.myProject.payload.request.SignupRequest;
import ru.ilmira.myProject.payload.request.TokenRefreshRequest;
import ru.ilmira.myProject.payload.response.JwtResponse;
import ru.ilmira.myProject.payload.response.TokenRefreshResponse;
import ru.ilmira.myProject.persist.model.User;
import ru.ilmira.myProject.util.token.RefreshTokenCreator;
import ru.ilmira.myProject.util.user.UserCreator;

import java.util.List;

public class AuthCreator {

    public static final String USERNAME = "user1";
    public static final String FIRSTNAME = "User1";

    public static final String EMAIL = "user@mail.ru";
    public static final String PASSWORD = "password";
    public static final String TOKEN = "token-test";
    public static final String TYPE = "Bearer";
    public static final String ROLE_USER = "ROLE_USER";

    public static final User USER = UserCreator.createUser();

    public static LoginRequest createLoginRequest() {
        return LoginRequest
                .builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    public static SignupRequest createSignupRequest() {
        return SignupRequest
                .builder()
                .username(USERNAME)
                .password(PASSWORD)
                .firstName(FIRSTNAME)
                .build();
    }

    public static JwtResponse createJwtResponse() {
        return JwtResponse.builder()
                .token(TOKEN)
                .type(TYPE)
                .refreshToken(RefreshTokenCreator.TOKEN)
                .username(USER.getUsername())
                .authorities(List.of(ROLE_USER))
                .build();
    }

    public static TokenRefreshRequest createTokenRefreshRequest() {
        return TokenRefreshRequest.builder()
                .refreshToken(RefreshTokenCreator.TOKEN)
                .build();
    }

    public static TokenRefreshResponse createTokenRefreshResponse() {
        return TokenRefreshResponse.builder()
                .accessToken(TOKEN)
                .refreshToken(RefreshTokenCreator.TOKEN)
                .tokenType(TYPE)
                .build();
    }

}

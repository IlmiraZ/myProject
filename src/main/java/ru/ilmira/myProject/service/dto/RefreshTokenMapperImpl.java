package ru.ilmira.myProject.service.dto;

import org.springframework.stereotype.Component;
import ru.ilmira.myProject.persist.model.RefreshToken;

@Component
public class RefreshTokenMapperImpl implements RefreshTokenMapper {

    @Override
    public RefreshTokenDto fromRefreshToken(RefreshToken refreshToken) {
        return RefreshTokenDto.builder()
                .id(refreshToken.getId())
                .userId(refreshToken.getUser().getId())
                .username(refreshToken.getUser().getUsername())
                .token(refreshToken.getToken())
                .expiryDate(refreshToken.getExpiryDate())
                .created(refreshToken.getCreated())
                .build();
    }
}

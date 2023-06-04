package ru.ilmira.myProject.service.dto;

import ru.ilmira.myProject.persist.model.RefreshToken;

public interface RefreshTokenMapper {

    RefreshTokenDto fromRefreshToken(RefreshToken refreshToken);
}

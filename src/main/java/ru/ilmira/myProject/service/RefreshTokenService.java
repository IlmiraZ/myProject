package ru.ilmira.myProject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ilmira.myProject.persist.model.User;
import ru.ilmira.myProject.service.dto.RefreshTokenDto;

import java.util.Optional;
@Service
public interface RefreshTokenService {

    Page<RefreshTokenDto> listAll(Pageable pageable);

    Page<RefreshTokenDto> listAllByUser(Pageable pageable, Long userId);

    Optional<RefreshTokenDto> findByToken(String token);

    RefreshTokenDto create(User user);

    RefreshTokenDto verifyExpiration(RefreshTokenDto token);

    void deleteByUserId(Long userId);
}

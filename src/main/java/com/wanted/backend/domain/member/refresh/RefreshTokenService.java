package com.wanted.backend.domain.member.refresh;

import com.wanted.backend.global.exception.CustomErrorCode;
import com.wanted.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenManagement refreshTokenManagement;

    public void save(String email, String refreshToken) {
        refreshTokenManagement.saveRefreshToken(email, refreshToken);
    }

    public String findByEmail(String email) {
        return refreshTokenManagement.getRefreshToken(email).orElseThrow(() -> {
            throw new CustomException(CustomErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }).toString();
    }

    public void delete(String email) {
        refreshTokenManagement.removeRefreshToken(email);
    }
}

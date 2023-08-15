package com.wanted.backend.domain.member.service;

import com.wanted.backend.domain.member.dto.AccessTokenRequest;
import com.wanted.backend.domain.member.dto.AccessTokenResponse;
import com.wanted.backend.domain.member.dto.LoginDto;
import com.wanted.backend.domain.member.entity.Member;
import com.wanted.backend.domain.member.entity.role.Role;
import com.wanted.backend.domain.member.refresh.RefreshTokenService;
import com.wanted.backend.global.exception.CustomErrorCode;
import com.wanted.backend.global.exception.CustomException;
import com.wanted.backend.domain.member.repository.MemberRepository;
import com.wanted.backend.global.jwt.AuthToken;
import com.wanted.backend.global.jwt.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wanted.backend.global.exception.CustomErrorCode.EXISTS_EMAIL;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;

    @Transactional
    public Member registerUser(String email, String password) {
        if (memberRepository.existsByEmail(email)) throw new CustomException(EXISTS_EMAIL);
        Member member = Member.of(email, password);
        member.encodePassword(passwordEncoder);
        memberRepository.save(member);
        return member;
    }

    @Transactional
    public ResponseEntity<AccessTokenResponse> signIn(LoginDto loginDto) {
        try {
            memberRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND));
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );
            AuthToken accessToken = tokenProvider.generateToken(loginDto.getEmail(), Role.ROLE_USER.name(), true);
            AuthToken refreshToken = tokenProvider.generateToken(loginDto.getEmail(), Role.ROLE_USER.name(), false);
            refreshTokenService.save(loginDto.getEmail(), refreshToken.getToken());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "Bearer " + accessToken.getToken());
            return new ResponseEntity<>(new AccessTokenResponse(accessToken.getToken()), httpHeaders, HttpStatus.OK);

        } catch (AuthenticationException e) {
            throw new CustomException(CustomErrorCode.PASSWORD_DO_NOT_MATCH);
        }
    }

    public ResponseEntity<AccessTokenResponse> getAccessToken(AccessTokenRequest accessTokenRequest) {
        String accessTokenValue = accessTokenRequest.getAccessToken();
        AuthToken accessToken = tokenProvider.convertAuthToken(accessTokenValue);
        String email = accessToken.getEmail();

        try {
            accessToken.validateToken();
            return new ResponseEntity<>(new AccessTokenResponse(accessTokenValue), null, HttpStatus.OK);
        } catch (ExpiredJwtException expiredJwtException) {
            String accessTkn = reIssueAccessTokenFromRefreshToken(email);
            return new ResponseEntity<>(new AccessTokenResponse(accessTkn), null, HttpStatus.OK);
        }
    }

    public String reIssueAccessTokenFromRefreshToken(String email) {
        String refreshTokenValue = refreshTokenService.findByEmail(email);
        AuthToken refreshToken = tokenProvider.convertAuthToken(refreshTokenValue);
        checkRefreshTokenValidation(refreshToken, refreshTokenValue, email);
        AuthToken accessToken = tokenProvider.generateToken(email, Role.ROLE_USER.name(), true);
        return accessToken.getToken();
    }

    public void removeToken(UserDetails userDetails) {
        String email = userDetails.getUsername();
        refreshTokenService.delete(email);
    }

    private void checkRefreshTokenValidation(AuthToken refreshToken, String refreshTokenValue, String email) {
        try {
            tokenProvider.convertAuthToken(refreshTokenValue).validateToken();

            if (!email.equals(refreshToken.getEmail())) {
                throw new CustomException(CustomErrorCode.ACCESS_TOKEN_INVALID);
            }

        } catch (ExpiredJwtException expiredJwtException) {
            throw new CustomException(CustomErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }
}

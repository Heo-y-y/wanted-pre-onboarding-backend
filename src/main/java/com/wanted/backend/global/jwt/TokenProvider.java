package com.wanted.backend.global.jwt;

import com.wanted.backend.domain.member.entity.role.Role;
import com.wanted.backend.global.exception.CustomErrorCode;
import com.wanted.backend.global.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "role";
    private final Key key;
    private final Long ACCESS_TOKEN_PERIOD = 1000L * 60L * 120L;
    private final Long REFRESH_TOKEN_ERIOD = 1000L * 60L * 60L * 24L * 14L;

    public TokenProvider(String secretKey) {
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKey.getBytes()));
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public AuthToken generateToken(String email, String role, boolean isAccessToken) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put(AUTHORITIES_KEY, role);
        Long duration = isAccessToken ? ACCESS_TOKEN_PERIOD : REFRESH_TOKEN_ERIOD;
        return AuthToken.builder()
                .claims(claims)
                .key(key)
                .duration(duration)
                .build();
    }

    public Authentication getAuthentication(AuthToken authToken) {
        if (!authToken.validateToken()) throw new CustomException(CustomErrorCode.ACCESS_TOKEN_INVALID);

        Claims claims = authToken.getTokenClaims();
        Collection<? extends GrantedAuthority> authorities = getGrantedAuthorities(claims);
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(Claims claims) {
        Role role = Role.valueOf((String) claims.get("role"));
        return Arrays.stream(new String[] {claims.get(AUTHORITIES_KEY).toString()})
                .map(c -> new SimpleGrantedAuthority(role.getKey()))
                .collect(Collectors.toList());
    }

    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) return bearerToken.substring(7);
        return null;
    }
}

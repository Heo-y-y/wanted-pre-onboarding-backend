package com.wanted.backend.global.config.security;

import com.wanted.backend.global.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.token.jwt-secret-key}")
    private String secretKey;

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(secretKey);
    }
}

package com.wanted.backend.domain.member.refresh;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RefreshTokenManagement {
    private final RedisTemplate<String, String> redisTemplate;
    private final String REFRESH_TOKEN_HASH_KEY = "refresh-token";

    public void saveRefreshToken(String email, String refreshToken) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        Map<String, String> userMap = new HashMap<>();
        userMap.put(REFRESH_TOKEN_HASH_KEY, refreshToken);
        hashOperations.putAll(email, userMap);
        redisTemplate.expire(email, 14, TimeUnit.DAYS);
    }

    public Optional<Object> getRefreshToken(String email) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        return Optional.ofNullable(hashOperations.get(email, REFRESH_TOKEN_HASH_KEY));
    }

    public void removeRefreshToken(String email) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(email, REFRESH_TOKEN_HASH_KEY);
    }
}

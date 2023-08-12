package com.wanted.backend.service;

import com.wanted.backend.entity.User;
import com.wanted.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void registerUser(String email, String password) {
        if (userRepository.existsByEmail(email)) throw new IllegalStateException("해당 이메일이 이미 존재합니다.");
        User user = User.of(email, password);
        userRepository.save(user);
    }
}

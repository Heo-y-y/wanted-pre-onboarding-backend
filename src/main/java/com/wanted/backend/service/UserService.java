package com.wanted.backend.service;

import com.wanted.backend.entity.User;
import com.wanted.backend.exception.CustomException;
import com.wanted.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wanted.backend.exception.CustomErrorCode.EXISTS_EMAIL;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void registerUser(String email, String password) {
        if (userRepository.existsByEmail(email)) throw new CustomException(EXISTS_EMAIL);
        User user = User.of(email, password);
        userRepository.save(user);
    }
}

package com.wanted.backend.controller;

import com.wanted.backend.dto.SignUpDto;
import com.wanted.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Validated @RequestBody SignUpDto signUp) {
        userService.registerUser(signUp.getEmail(), signUp.getPassword());
        return ResponseEntity.ok().build();
    }
}

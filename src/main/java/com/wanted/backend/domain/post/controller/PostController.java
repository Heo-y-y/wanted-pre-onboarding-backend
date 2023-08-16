package com.wanted.backend.domain.post.controller;

import com.wanted.backend.domain.post.dto.PostCreateDto;
import com.wanted.backend.domain.post.service.PostService;
import com.wanted.backend.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<ApiResponse<PostCreateDto>> createPost(@Valid @RequestBody PostCreateDto postCreateDto, @AuthenticationPrincipal UserDetails userDetails) {
        postService.createPost(postCreateDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.postCreated(postCreateDto));
    }
}

package com.wanted.backend.domain.post.controller;

import com.wanted.backend.domain.post.dto.PostCreateDto;
import com.wanted.backend.domain.post.dto.PostUpdateDto;
import com.wanted.backend.domain.post.dto.SearchPostsOfTheID;
import com.wanted.backend.domain.post.service.PostService;
import com.wanted.backend.global.exception.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Api(tags = "postController")
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @ApiOperation(value = "게시글 등록")
    @PostMapping("/post")
    public ResponseEntity<ApiResponse<PostCreateDto>> createPost(@Valid @RequestBody PostCreateDto postCreateDto, @AuthenticationPrincipal UserDetails userDetails) {
        postService.createPost(postCreateDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createPost(postCreateDto));
    }

    @ApiOperation(value = "게시글 수정")
    @PatchMapping("/post/{post-id}")
    public ResponseEntity<ApiResponse<PostUpdateDto>> updatePost(@PathVariable("post-id") Long postId, @Valid @RequestBody PostUpdateDto postUpdateDto, @AuthenticationPrincipal UserDetails userDetails) {
        postService.updatePost(postId, postUpdateDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.updatePost(postUpdateDto));
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/post/{post-id}")
    public ResponseEntity<ApiResponse<Object>> deletePost(@PathVariable("post-id") Long postId, @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(postId, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.deletePost());
    }

    @ApiOperation(value = "게시글 조회")
    @GetMapping("/post/{post-id}")
    public ResponseEntity<ApiResponse<SearchPostsOfTheID>> getPost(@PathVariable("post-id") Long postId, SearchPostsOfTheID searchPostsOfTheID) {
        return postService.getSearchPost(postId);
    }
}

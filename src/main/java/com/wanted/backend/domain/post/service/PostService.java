package com.wanted.backend.domain.post.service;

import com.wanted.backend.domain.member.entity.Member;
import com.wanted.backend.domain.member.repository.MemberRepository;
import com.wanted.backend.domain.post.dto.*;
import com.wanted.backend.domain.post.entity.Post;
import com.wanted.backend.domain.post.repository.PostRepository;
import com.wanted.backend.global.common.MultiResponseDto;
import com.wanted.backend.global.exception.ApiResponse;
import com.wanted.backend.global.exception.CustomErrorCode;
import com.wanted.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public final MemberRepository memberRepository;

    @Transactional
    public void createPost(PostCreateDto postCreateDto, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND));
        Post post = Post.of(member, postCreateDto.getTitle(), postCreateDto.getContent());
        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateDto postUpdateDto, String email) {
        Post post = checkPermission(postId, email);
        if (postUpdateDto.getTitle() != null) post.setTitle(postUpdateDto.getTitle());
        if (postUpdateDto.getContent() != null) post.setContent(postUpdateDto.getContent());
    }

    private Post checkPermission(Long postId, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND));
        Post post = postRepository.findByIdAndIsDeleted(postId, false).orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUNT));
        if (post.getMemberId() != member) throw new CustomException(CustomErrorCode.POST_CANNOT_ACCESS);
        return post;
    }

    @Transactional
    public void deletePost(Long postId, String email) {
        Post post = checkPermission(postId, email);
        post.deletePost();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<SearchPostsOfTheID>> getSearchPost(Long postId) {
        Post post = postRepository.findByIdAndIsDeleted(postId, false).orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUNT));
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.getPost(new SearchPostsOfTheID(post)));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<MultiResponseDto<SearchPostsOfTheID>>> getPosts(int page, int size) {
        Page<Post> pages = postRepository.findAll(PageRequest.of(page, size));
//        Page<Post> pages = postRepository.findAll(PageRequest.of(page, size));
        List<SearchPostsOfTheID> searchPostsOfTheIDS = SearchPostsOfTheID.listOf(pages.getContent());
        PageInfo pageInfo = PageInfo.of(pages);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.getPosts(MultiResponseDto.of(searchPostsOfTheIDS, pageInfo)));
    }
}

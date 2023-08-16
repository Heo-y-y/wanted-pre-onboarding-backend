package com.wanted.backend.domain.post.service;

import com.wanted.backend.domain.member.entity.Member;
import com.wanted.backend.domain.member.repository.MemberRepository;
import com.wanted.backend.domain.post.dto.PostCreateDto;
import com.wanted.backend.domain.post.dto.PostUpdateDto;
import com.wanted.backend.domain.post.entity.Post;
import com.wanted.backend.domain.post.repository.PostRepository;
import com.wanted.backend.global.exception.CustomErrorCode;
import com.wanted.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

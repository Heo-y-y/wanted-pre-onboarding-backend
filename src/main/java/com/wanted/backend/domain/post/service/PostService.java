package com.wanted.backend.domain.post.service;

import com.wanted.backend.domain.member.entity.Member;
import com.wanted.backend.domain.member.repository.MemberRepository;
import com.wanted.backend.domain.post.dto.PostCreateDto;
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
}

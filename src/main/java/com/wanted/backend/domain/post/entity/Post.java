package com.wanted.backend.domain.post.entity;

import com.wanted.backend.domain.member.entity.Member;
import com.wanted.backend.domain.post.dto.PostCreateDto;
import com.wanted.backend.global.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import javax.persistence.*;
@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;
    private boolean delete;

    public static Post of(Member memberId, String title, String content) {
        return Post.builder()
                .title(title)
                .content(content)
                .memberId(memberId)
                .build();
    }
}

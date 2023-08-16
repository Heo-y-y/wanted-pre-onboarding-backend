package com.wanted.backend.domain.post.entity;

import com.wanted.backend.domain.member.entity.Member;
import com.wanted.backend.global.common.BaseTimeEntity;
import lombok.*;

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
    @Setter
    private String title;
    @Column(nullable = false)
    @Setter
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;
    @Column
    private Boolean isDeleted;

    public static Post of(Member memberId, String title, String content) {
        return Post.builder()
                .memberId(memberId)
                .title(title)
                .content(content)
                .isDeleted(false)
                .build();
    }
}

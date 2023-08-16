package com.wanted.backend.domain.post.dto;

import com.wanted.backend.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchPostsOfTheID {
    private String author;
    private String title;
    private String content;

    public SearchPostsOfTheID(Post post) {
        this.author = post.getMemberId().getEmail();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}

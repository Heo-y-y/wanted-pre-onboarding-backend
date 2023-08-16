package com.wanted.backend.domain.post.dto;

import com.wanted.backend.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
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

    public static SearchPostsOfTheID of(Post post) {
        SearchPostsOfTheID searchPostsOfTheID = new SearchPostsOfTheID();
        searchPostsOfTheID.setAuthor(post.getMemberId().getEmail());
        searchPostsOfTheID.setTitle(post.getTitle());
        searchPostsOfTheID.setContent(post.getContent());
        return searchPostsOfTheID;
    }

    public static List<SearchPostsOfTheID> listOf(List<Post> posts) {
        return posts.stream().map(SearchPostsOfTheID::of).collect(Collectors.toList());
    }
}

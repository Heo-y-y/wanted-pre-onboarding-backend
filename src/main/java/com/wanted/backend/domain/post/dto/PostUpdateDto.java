package com.wanted.backend.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PostUpdateDto {
    private String title;
    private String content;
}

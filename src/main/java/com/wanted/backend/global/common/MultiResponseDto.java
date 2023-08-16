package com.wanted.backend.global.common;

import com.wanted.backend.domain.post.dto.PageInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiResponseDto<T> {
    private List<T> data;
    private PageInfo pageInfo;

    public static <T> MultiResponseDto<T> of(List<T> data, PageInfo pageInfo) {
        return new MultiResponseDto<>(data, pageInfo);
    }
}

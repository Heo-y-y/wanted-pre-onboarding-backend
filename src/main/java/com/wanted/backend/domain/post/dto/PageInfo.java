package com.wanted.backend.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class PageInfo {
    private int page;
    private int size;
    private int totalPage;
    private long totalSize;

    public static <T> PageInfo of(Page<T> page) {
        return new PageInfo(page.getNumber() + 1, page.getSize(), page.getTotalPages(), page.getTotalElements());
    }
}

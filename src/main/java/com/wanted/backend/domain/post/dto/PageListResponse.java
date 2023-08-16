//package com.wanted.backend.domain.post.dto;
//
//import com.wanted.backend.domain.post.entity.Post;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.domain.Page;
//
//import java.util.List;
//
//@Getter
//@Setter
//@AllArgsConstructor
//public class PageListResponse<T> {
//    private List<T> data;
//    private PageInfo pageInfo;
//
//    public static PageListResponse<Post> of(List<Post> data, Page pages) {
//        return new PageListResponse(data, new PageInfo(pages.getNumber() + 1, pages.getSize(), pages.getTotalElements(), pages.getTotalPages()));
//    }
//}

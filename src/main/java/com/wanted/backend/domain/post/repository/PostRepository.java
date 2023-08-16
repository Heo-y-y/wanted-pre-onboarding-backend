package com.wanted.backend.domain.post.repository;

import com.wanted.backend.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.id = :postId AND p.isDeleted = :isDeleted")
    Optional<Post> findByIdAndIsDeleted(Long postId, Boolean isDeleted);
    Page<Post> findAll(Pageable pageable);
}

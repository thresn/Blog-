package com.BlogProject.Blog.repositories;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogProject.Blog.domain.Category;
import com.BlogProject.Blog.domain.Post;
import com.BlogProject.Blog.domain.Tag;
import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.enums.PostStatus;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);
    List<Post> findAllByStatus(PostStatus status);
    List<Post> findAllByAuthorAndStatus(User author,PostStatus status);
}

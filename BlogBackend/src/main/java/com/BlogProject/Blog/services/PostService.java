package com.BlogProject.Blog.services;

import java.util.List;
import java.util.UUID;

import com.BlogProject.Blog.domain.CreatePostRequest;
import com.BlogProject.Blog.domain.Post;
import com.BlogProject.Blog.domain.UpdatePostRequest;
import com.BlogProject.Blog.domain.User;


public interface PostService {
    List<Post> getAllPosts(UUID categoryId,UUID tagId);
    List<Post> getDrafPosts(User user);
    Post createPost(User user,CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    Post getPost(UUID id);
    void deletePost(UUID id);

}

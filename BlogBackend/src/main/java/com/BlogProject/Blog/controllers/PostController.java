package com.BlogProject.Blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BlogProject.Blog.domain.CreatePostRequest;
import com.BlogProject.Blog.domain.Post;
import com.BlogProject.Blog.domain.UpdatePostRequest;
import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.domain.dtos.CreatePostRequestDto;
import com.BlogProject.Blog.domain.dtos.PostDto;
import com.BlogProject.Blog.domain.dtos.UpdatePostRequestDto;
import com.BlogProject.Blog.mappers.PostMapper;
import com.BlogProject.Blog.services.PostService;
import com.BlogProject.Blog.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId) {
        List<Post> posts = postService.getAllPosts(categoryId, tagId);
        List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(postDtos);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {
        User user = userService.getUserById(userId);
        List<Post> usersDraftPosts = postService.getDrafPosts(user);
        List<PostDto> draftsDto = usersDraftPosts.stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(draftsDto);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody CreatePostRequestDto createPostRequestDto,
            @RequestAttribute UUID userId) {
        User user = userService.getUserById(userId);
        CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
        Post createdPost = postService.createPost(user, createPostRequest);
        PostDto createdPostDto = postMapper.toDto(createdPost);
        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto) {
        UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);
        Post updatedPost = postService.updatePost(id, updatePostRequest);
        PostDto updatedPostDto = postMapper.toDto(updatedPost);
        return ResponseEntity.ok(updatedPostDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable UUID id){
        Post post=postService.getPost(id);
        PostDto postDto=postMapper.toDto(post);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}

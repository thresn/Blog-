package com.BlogProject.Blog.services.Impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.BlogProject.Blog.domain.Category;
import com.BlogProject.Blog.domain.CreatePostRequest;
import com.BlogProject.Blog.domain.Post;
import com.BlogProject.Blog.domain.Tag;
import com.BlogProject.Blog.domain.UpdatePostRequest;
import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.domain.dtos.UpdatePostRequestDto;
import com.BlogProject.Blog.enums.PostStatus;
import com.BlogProject.Blog.repositories.PostRepository;
import com.BlogProject.Blog.services.CategoryService;
import com.BlogProject.Blog.services.PostService;
import com.BlogProject.Blog.services.TagService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final static int WORDS_PER_MINUTE = 200;
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final TagService tagService;

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {

        if (categoryId != null && tagId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(PostStatus.PUBLISHED, category, tag);
        }

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            return postRepository.findAllByStatusAndCategory(PostStatus.PUBLISHED, category);
        }

        if (tagId != null) {
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
        }

        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDrafPosts(User user) {
        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Transactional
    @Override
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        LocalDateTime now = LocalDateTime.now();
        newPost.setCreatedAt(now);
        newPost.setUpdatedAt(now);

        Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagsId();
        List<Tag> tags = tagService.getTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    private Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) {
            return 0;
        }
        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("post bulunamadı bu id'li: " + id));

        String currentUsername = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();

        // 3. Postun asıl sahibinin email bilgisi ile istek atan kişinin bilgisi
        // uyuşuyor mu?
        if (!existingPost.getAuthor().getEmail().equals(currentUsername)) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Bu postu güncellemeye yetkiniz yok! Sadece kendi yazdığınız postları düzenleyebilirsiniz.");
        }

        existingPost.setTitle(updatePostRequest.getTitle());
        existingPost.setContent(updatePostRequest.getContent());
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(updatePostRequest.getContent()));

        UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();

        if (!updatePostRequest.getCategoryId().equals(updatePostRequestCategoryId)) {
            Category newCategory = categoryService.getCategoryById(updatePostRequestCategoryId);
            existingPost.setCategory(newCategory);
        }

        Set<UUID> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagsId();

        if (!existingTagIds.equals(updatePostRequestTagIds)) {
            List<Tag> newTags = tagService.getTagByIds(updatePostRequestTagIds);
            existingPost.setTags(new HashSet<>(newTags));
        }

        return postRepository.save(existingPost);
    }

    @Override
    public Post getPost(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("post bulunamadı"));
    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }

}

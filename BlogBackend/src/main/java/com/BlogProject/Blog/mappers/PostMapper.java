package com.BlogProject.Blog.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.BlogProject.Blog.domain.CreatePostRequest;
import com.BlogProject.Blog.domain.Post;
import com.BlogProject.Blog.domain.UpdatePostRequest;
import com.BlogProject.Blog.domain.dtos.CreatePostRequestDto;
import com.BlogProject.Blog.domain.dtos.PostDto;
import com.BlogProject.Blog.domain.dtos.UpdatePostRequestDto;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);
    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto updatePostRequestDto);
}

package com.BlogProject.Blog.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.BlogProject.Blog.domain.Category;
import com.BlogProject.Blog.domain.Post;
import com.BlogProject.Blog.domain.dtos.CategoryDto;
import com.BlogProject.Blog.domain.dtos.CreateCategoryRequest;
import com.BlogProject.Blog.enums.PostStatus;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posts", ignore = true)
    Category toEntity(CreateCategoryRequest createCategoryRequest);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts) {
        if (posts == null) {
            return 0;
        }
        return posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getStatus())).count();
    }
}

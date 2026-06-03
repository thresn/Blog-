package com.BlogProject.Blog.mappers;

import org.mapstruct.Mapper;

import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.domain.dtos.AdminUserResponseDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AdminUserResponseDto toAdminUserResponseDto(User user);
}

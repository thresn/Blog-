package com.BlogProject.Blog.services;

import java.util.List;
import java.util.UUID;

import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.domain.dtos.AdminStatsDto;
import com.BlogProject.Blog.domain.dtos.AdminUserCreateDto;

public interface AdminService {
    AdminStatsDto getStats();
    List<User> getsUsers();
    User createUser(AdminUserCreateDto adminUserCreateDto);
    void deleteUser(UUID id);
}

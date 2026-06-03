package com.BlogProject.Blog.services;

import java.util.UUID;

import com.BlogProject.Blog.domain.User;

public interface UserService {
    User getUserById(UUID id);

}

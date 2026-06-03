package com.BlogProject.Blog.services.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.domain.dtos.AdminStatsDto;
import com.BlogProject.Blog.domain.dtos.AdminUserCreateDto;
import com.BlogProject.Blog.repositories.CategoryRepository;
import com.BlogProject.Blog.repositories.PostRepository;
import com.BlogProject.Blog.repositories.TagRepository;
import com.BlogProject.Blog.repositories.UserRepository;
import com.BlogProject.Blog.services.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminStatsDto getStats() {
        Long postCount = postRepository.count();
        Long categoryCount = categoryRepository.count();
        Long tagCount = tagRepository.count();

        return AdminStatsDto.builder().PostCount(postCount).CategoryCount(categoryCount).TagCount(tagCount).build();
    }

    @Override
    public List<User> getsUsers() {
        List<User> users = userRepository.findAll();
        return users;

    }

    @Override
    public User createUser(AdminUserCreateDto adminUserCreateDto) {

        String encodedPassword = passwordEncoder.encode(adminUserCreateDto.getPassword());

        User newUser=User.builder()
        .name(adminUserCreateDto.getName())
        .email(adminUserCreateDto.getEmail())
        .password(encodedPassword).build();

        return userRepository.save(newUser);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}

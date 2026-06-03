package com.BlogProject.Blog.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.domain.dtos.AdminStatsDto;
import com.BlogProject.Blog.domain.dtos.AdminUserCreateDto;
import com.BlogProject.Blog.domain.dtos.AdminUserResponseDto;
import com.BlogProject.Blog.mappers.UserMapper;
import com.BlogProject.Blog.repositories.UserRepository;
import com.BlogProject.Blog.services.AdminService;
import com.BlogProject.Blog.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserMapper userMapper;

    @GetMapping(path = "/stats")
    public ResponseEntity<AdminStatsDto> getAllStats() {
        return ResponseEntity.ok(adminService.getStats());
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<AdminUserResponseDto>> getUsers() {
        List<User> users = adminService.getsUsers();
        List<AdminUserResponseDto> usersDto = users.stream().map(userMapper::toAdminUserResponseDto).toList();
        return ResponseEntity.ok(usersDto);
    }

    @PostMapping(path = "/users")
    public ResponseEntity<AdminUserResponseDto> createUser(@RequestBody AdminUserCreateDto adminUserCreateDto) {
        User newUser = adminService.createUser(adminUserCreateDto);
        AdminUserResponseDto newUserDto = userMapper.toAdminUserResponseDto(newUser);
        return new ResponseEntity<>(newUserDto, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        adminService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.BlogProject.Blog.services.Impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.repositories.UserRepository;
import com.BlogProject.Blog.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final  UserRepository userRepository;
    @Override
    public User getUserById(UUID id) {
       return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User bulunamadı"));
    }

}

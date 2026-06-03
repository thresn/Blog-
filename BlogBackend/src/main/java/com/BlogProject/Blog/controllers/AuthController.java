package com.BlogProject.Blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogProject.Blog.domain.dtos.AuthResponse;
import com.BlogProject.Blog.domain.dtos.LoginRequest;
import com.BlogProject.Blog.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/auth/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        UserDetails userDetails=authenticationService.authanticate(loginRequest.getEmail(),loginRequest.getPassword());
        String token=authenticationService.generateToken(userDetails);
        AuthResponse authResponse=AuthResponse.builder().token(token).expiresIn(86400).build();
        return ResponseEntity.ok(authResponse);
    }

}

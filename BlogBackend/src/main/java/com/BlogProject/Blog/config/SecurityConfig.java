package com.BlogProject.Blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.BlogProject.Blog.repositories.UserRepository;
import com.BlogProject.Blog.security.BlogUserDetailsService;
import com.BlogProject.Blog.security.JwtAuthenticationFilter;
import com.BlogProject.Blog.services.AuthenticationService;

//Dış kütüphane kullanıyorsan: (Örn: PasswordEncoder veya AuthenticationManager).
// O sınıfların kodu senin elinde değil, içine girip @Component yazamazsın.
@Configuration

// Özel ayar gerekiyorsa: Nesne oluşurken içine özel parametreler vermek
// istiyorsan.
// İşte o zaman @Configuration içinde bir @Bean metodu yazar, nesneyi
// kendi ellerinle new yaparak oluşturur ve Spring'e "Al bunu sen yönet" dersin.
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            AuthenticationService authenticationService) {
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new BlogUserDetailsService(userRepository); // Sadece görevi olan şeyi yapmalı
    }

    // Gelen her bir HTTP isteği (Request) bu süzgeçten geçer.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter)
            throws Exception {
        // 1. Kural: GET istekleri (Okuma) serbest
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/drafts").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
                        .anyRequest().authenticated()) // 2. Kural: Geri kalan her şey (Yazma, Silme, Güncelleme)
                                                       // kilitli
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // fonksiyona giren malzeme(s)| o malzeme ile ne yapacağım (s.session.....)
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

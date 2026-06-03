package com.BlogProject.Blog.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.BlogProject.Blog.domain.User;
import com.BlogProject.Blog.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
       User user=  userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Bu maille user bulunamadı:"+email));
       return new BlogUserDetails(user);
    }

}

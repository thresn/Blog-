package com.BlogProject.Blog.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.BlogProject.Blog.services.AuthenticationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = extractToken(request);//ayrıştırılan token
            if (token != null) {
                UserDetails userDetails = authenticationService.validateToken(token);//service de doğrulayıp emaili okudu
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,//user bilgileri
                        null, //normalde şifre konur, ama biz zaten valideteledik.
                        userDetails.getAuthorities());//rol
                SecurityContextHolder.getContext().setAuthentication(authentication); //üsteki doğrulanmış kullanııcılar artık içeride özgür setledik
                // Add userId to request attributes for controller access
                if (userDetails instanceof BlogUserDetails) {
                    request.setAttribute("userId", ((BlogUserDetails) userDetails).getId()); //ileride Controller içinde "Şu an işlem yapan kişinin ID'si kaçtı?" diye veri tabanına tekrar gitmeden bu bilgiyi kullanmanı sağlar.
                }
            }
        } catch (Exception e) {
            // Don't throw exceptions here - just don't authenticate the request
            log.warn("Received invalid auth token");
        }
        filterChain.doFilter(request, response);
    }

    //istekten gelen Tokeni saf halini ayırıyoruz bearer kısımını atıp
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}

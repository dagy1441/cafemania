package com.dagy.cafemania.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // cette aproche une boucle entre le beans/ dependance
    //  private final UserServiceImpl userDetailsService;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;
    Claims claims;
    private String userEmail = null;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        //request.getServletPath().contains("/api/v1/auth")
        if (request.getServletPath().contains("/cafemania/api/v1/auth")) {
            System.out.println("request.getServletPath " + request.getServletPath());
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
//        final String userEmail;
//        final Claims claims;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        claims = jwtService.extractAllClaims(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//            var isTokenValid = tokenRepository.findByToken(jwt)
//                    .map(t -> !t.isExpired() && !t.isRevoked())
//                    .orElse(false);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase((String) claims.get("roles"));
    }

    public boolean isUser() {
        return "user".equalsIgnoreCase((String) claims.get("roles"));
    }

    public String getCurrentUser() {
        return userEmail;
    }

}

package com.travelgo_payment_service.filter;

import com.travelgo_payment_service.service.JwtService;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.travelgo_payment_service.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import io.micrometer.common.lang.NonNull;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
        System.out.println("🔥 PAYMENT JWT FILTER BEAN CREATED");

    }
    
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("========== PAYMENT JWT FILTER EXECUTED ==========");

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = jwtService.extractUsername(token);

            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                if (jwtService.validateToken(token, email)) {

                    Long userId = jwtService.extractUserId(token);
                    String role = jwtService.extractRole(token);

                    System.out.println("Email = " + email);
                    System.out.println("User ID = " + userId);
                    System.out.println("Role = " + role);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userId,
                                    null,
                                    List.of(
                                            new SimpleGrantedAuthority(
                                                    "ROLE_" + role)
                                    )
                            );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);

                    System.out.println(
                            "Payment Authentication Successfully Set");
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Payment JWT validation failed: " + e.getMessage());

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

}

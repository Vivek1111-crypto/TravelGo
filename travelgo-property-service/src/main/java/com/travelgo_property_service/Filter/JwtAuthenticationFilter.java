package com.travelgo_property_service.Filter;

//package com.travelgo.property.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.travelgo_property_service.Service.JwtService;

//import com.travelgo.property.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
    		
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("========== JWT FILTER EXECUTED ==========");


        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = jwtService.extractUsername(token);
            System.out.println("Email from JWT = " + email);

            if (email != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                if (jwtService.validateToken(token, email)) {

                	String role = jwtService.extractRole(token);
                	System.out.println("Role from JWT = " + role);
                	System.out.println("Token Valid = " + jwtService.validateToken(token, email));
                	UsernamePasswordAuthenticationToken authentication =
                	        new UsernamePasswordAuthenticationToken(
                	                email,
                	                null,
                	                List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
    
}

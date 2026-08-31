package com.travelgo.auth.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.travelgo.auth.util.JwtService;

//import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
	private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService) {

        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
    	// Read Authorization header
        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);
        System.out.println("Jwt Filter Executed");


        // If header is missing or doesn't start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token
        String jwt = authHeader.substring(7);

        // Extract username(email) from token
        String username = jwtService.extractUsername(jwt);
        System.out.println("Username from token: " + username);

        // Authenticate only if not already authenticated
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    customUserDetailsService.loadUserByUsername(username);
            System.out.println("Username from DB: " + userDetails.getUsername());

            boolean valid = jwtService.isTokenValid(jwt, userDetails.getUsername());

            System.out.println("Token Valid = " + valid);

            if (valid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
                System.out.println("Authentication Successfully Set");
                System.out.println("Authorities: " + authToken.getAuthorities());
            }
        }

        filterChain.doFilter(request, response);
        System.out.println("Security Context = " +
                SecurityContextHolder.getContext().getAuthentication());


}

}

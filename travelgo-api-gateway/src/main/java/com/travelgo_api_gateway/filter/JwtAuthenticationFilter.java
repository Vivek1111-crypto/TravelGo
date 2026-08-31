package com.travelgo_api_gateway.filter;
//package com.travelgo.api_gateway.filter;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        System.out.println("===== GATEWAY FILTER =====");
        System.out.println("Path = " + path);

        // PUBLIC APIs - JWT check mat karo
        if (path.startsWith("/api/auth/")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs")) {

            System.out.println("Public API - Skipping JWT");
            return chain.filter(exchange);
        }

        // Ab Authorization header check karo
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        System.out.println("Authorization = " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);

            System.out.println("JWT VALID IN GATEWAY");
            return chain.filter(exchange);

        } catch (Exception e) {
            System.out.println("JWT ERROR IN GATEWAY");
            e.printStackTrace();        // <-- IMPORTANT
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
    private SecretKey getSignInKey() {

    	
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
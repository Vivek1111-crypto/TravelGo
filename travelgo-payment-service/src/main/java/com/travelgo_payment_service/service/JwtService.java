package com.travelgo_payment_service.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	 @Value("${jwt.secret-key}")
	    private String secretKey;
	 

	    public String extractUsername(String token) {
	        return extractAllClaims(token).getSubject();
	    }
	    public Long extractUserId(String token) {
	        return extractAllClaims(token)
	                .get("userId", Long.class);
	    }
	    
	    public String extractRole(String token) {
	        return extractAllClaims(token)
	                .get("role", String.class);
	    }

	    public boolean validateToken(String token, String username) {

	        String extractedUsername = extractUsername(token);

	        return extractedUsername.equals(username)
	                && !isTokenExpired(token);
	    }

	    private boolean isTokenExpired(String token) {
	        return extractAllClaims(token)
	                .getExpiration()
	                .before(new Date());
	    }
	    private Claims extractAllClaims(String token) {

	        return Jwts.parser()
	                .verifyWith(getSignInKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }
	    private SecretKey getSignInKey() {

	        return Keys.hmacShaKeyFor(
	                Decoders.BASE64.decode(secretKey));
	    }
}


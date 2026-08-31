package com.travelgo_Booking_service.service;
import org.springframework.stereotype.Service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
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
	        return extractAllClaims(token).get("userId", Long.class);
	    }
	  public String extractRole(String token) {
	        return extractAllClaims(token).get("role", String.class);
	    }
	  private Claims extractAllClaims(String token) {

	        SecretKey key =
	                Keys.hmacShaKeyFor(
	                        Decoders.BASE64.decode(secretKey));

	        return Jwts.parser()
	                .verifyWith(key)
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
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
}

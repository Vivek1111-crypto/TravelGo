package com.travelgo.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.travelgo.auth.entity.RefreshToken;
import com.travelgo.auth.entity.User;
import com.travelgo.auth.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }
    public RefreshToken createRefreshToken(User user) {

        Optional<RefreshToken> existingToken =
                refreshTokenRepository.findByUser(user);

        RefreshToken refreshToken;

        if (existingToken.isPresent()) {
            refreshToken = existingToken.get();
        } else {
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
        }

        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken.setExpiryDate(
                LocalDateTime.now().plusDays(7));

        return refreshTokenRepository.save(refreshToken);
    }
    public Optional<RefreshToken> findByToken(String token) {

        return refreshTokenRepository.findByToken(token);
    }
    public boolean isExpired(RefreshToken token) {

        return token.getExpiryDate().isBefore(LocalDateTime.now());
    }
    public void deleteByUser(User user) {

        refreshTokenRepository.deleteByUser(user);
    }
    public RefreshToken verifyRefreshToken(String token) {
    	 RefreshToken refreshToken = refreshTokenRepository
    	            .findByToken(token)
    	            .orElseThrow(() ->
    	                    new RuntimeException("Refresh Token not found"));

    	    if (refreshToken.isRevoked()) {
    	        throw new RuntimeException("Refresh Token has been revoked");
    	    }

    	    if (isExpired(refreshToken)) {
    	        refreshTokenRepository.delete(refreshToken);
    	        throw new RuntimeException("Refresh Token Expired");
    	    }

    	    return refreshToken;
    }
}

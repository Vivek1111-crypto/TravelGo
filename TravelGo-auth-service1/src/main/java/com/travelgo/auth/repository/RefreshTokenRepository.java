package com.travelgo.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travelgo.auth.entity.RefreshToken;
import com.travelgo.auth.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long > {
	 Optional<RefreshToken> findByToken(String token);

	    Optional<RefreshToken> findByUser(User user);

	    void deleteByUser(User user);
}

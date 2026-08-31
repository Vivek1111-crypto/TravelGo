package com.travelgo.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.travelgo.auth.dto.LoginRequest;
import com.travelgo.auth.dto.LoginResponse;
import com.travelgo.auth.dto.RefreshTokenRequest;
import com.travelgo.auth.dto.RefreshTokenResponse;
import com.travelgo.auth.dto.RegisterRequest;
import com.travelgo.auth.entity.RefreshToken;
import com.travelgo.auth.entity.Role;
import com.travelgo.auth.entity.User;
import com.travelgo.auth.exception.UserAlreadyExistsException;
import com.travelgo.auth.repository.RefreshTokenRepository;
import com.travelgo.auth.repository.UserRepository;
import com.travelgo.auth.util.JwtService;

@Service
public class AuthServiceImpl implements AuthService {
	private final RefreshTokenService refreshTokenService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	   private final UserRepository userRepository;
	  
	    private final PasswordEncoder passwordEncoder;
	    private final RefreshTokenRepository refreshTokenRepository;

	    public AuthServiceImpl(UserRepository userRepository,
                PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager, JwtService jwtService,RefreshTokenService refreshTokenService,RefreshTokenRepository refreshTokenRepository) {
this.userRepository = userRepository;
this.passwordEncoder = passwordEncoder;
this.authenticationManager=authenticationManager;
this.jwtService=jwtService;
this.refreshTokenService=refreshTokenService;
this. refreshTokenRepository= refreshTokenRepository;
}
	    
	    
	@Override
	public String register(RegisterRequest request) {
		// TODO Auto-generated method stub
		User user = new User();
		if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email already registered");
        }
		

       

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.USER);

        userRepository.save(user);

        return "User Registered Successfully";
    }


	@Override
	public LoginResponse login(LoginRequest request) {

	    authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    request.getEmail(),
	                    request.getPassword()
	            )
	    );

	    User user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow(() ->
	                    new UsernameNotFoundException("User not found"));

	    System.out.println("Role from DB = " + user.getRole().name());
	    String accessToken = jwtService.generateToken(
	    	    user.getId(),
	            user.getEmail(),
	            user.getRole().name()
	    );
	    System.out.println("Access Token = " + accessToken);
	    RefreshToken refreshToken =
	            refreshTokenService.createRefreshToken(user);

	    return new LoginResponse(
	            accessToken,
	            refreshToken.getToken(),
	            user.getEmail(),
	            user.getFirstName(),
	            user.getLastName(),
	            user.getRole().name()
	    );
	}
	@Override
	public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

	    RefreshToken refreshToken =
	            refreshTokenService.verifyRefreshToken(
	                    request.getRefreshToken());
	    String accessToken =
	            jwtService.generateToken(
	            	    refreshToken.getUser().getId(),
	                    refreshToken.getUser().getEmail(),
	                    refreshToken.getUser().getRole().name()
	            );
	    return new RefreshTokenResponse(accessToken);
	}
	@Override
	public String logout(String refreshToken) {

	    RefreshToken token = refreshTokenRepository
	            .findByToken(refreshToken)
	            .orElseThrow(() ->
	                    new RuntimeException("Refresh token not found"));

	    token.setRevoked(true);

	    refreshTokenRepository.save(token);

	    return "Logged out successfully";
	}	
}

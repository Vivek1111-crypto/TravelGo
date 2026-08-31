package com.travelgo.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelgo.auth.dto.LoginRequest;
import com.travelgo.auth.dto.LoginResponse;
import com.travelgo.auth.dto.LogoutRequest;
import com.travelgo.auth.dto.RefreshTokenRequest;
import com.travelgo.auth.dto.RefreshTokenResponse;
import com.travelgo.auth.dto.RegisterRequest;
import com.travelgo.auth.service.AuthService;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/auth")
public class AuthController {
private final AuthService authservice;

public AuthController(AuthService authservice) {
	this.authservice=authservice;
	
}
@PostMapping("/register")
public ResponseEntity<String>register(
		@Validated @RequestBody RegisterRequest request
		){
	  System.out.println("Controller reached");
	return ResponseEntity.ok(
			authservice.register(request));
	

}
@PostMapping("/login")
public ResponseEntity<LoginResponse> login(
	    @RequestBody LoginRequest request) {

	    return ResponseEntity.ok(
	            authservice.login(request));
}
@PostMapping("/refresh-token")
public ResponseEntity<RefreshTokenResponse> refreshToken(
        @RequestBody RefreshTokenRequest request) {

    return ResponseEntity.ok(
            authservice.refreshToken(request));
}
@PostMapping("/logout")
public ResponseEntity<String> logout(
        @RequestBody LogoutRequest request) {

    authservice.logout(request.getRefreshToken());

    return ResponseEntity.ok("Logged out successfully");
}
}

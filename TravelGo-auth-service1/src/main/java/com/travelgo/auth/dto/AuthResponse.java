package com.travelgo.auth.dto;

public class AuthResponse {
	  public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	  public AuthResponse(String token) {
		super();
		this.token = token;
	}

	  private String token;
}

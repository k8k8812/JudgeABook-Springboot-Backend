package com.cognixia.jump.model;

public class AuthenticationResponse {

	// this is the token that will be sent back
	private final String JWT;
	
	public AuthenticationResponse(String JWT) {
		this.JWT = JWT;
	}
	
	public String getJWT() {
		return JWT;
	}
	
}

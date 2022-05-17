package com.cognixia.jump.exceptions;

public class InvalidLoginException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidLoginException(String password) {
		super("Username and password does not match our records!");
	}
	
}

package com.cognixia.jump.exceptions;


public class InvalidLoginException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidLoginException(String message, Throwable cause) {
		super("Username and password does not match our records!", cause);
	}
	
}

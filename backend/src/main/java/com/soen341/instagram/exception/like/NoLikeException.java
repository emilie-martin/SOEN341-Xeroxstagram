package com.soen341.instagram.exception.like;

public class NoLikeException extends RuntimeException{

	public NoLikeException() {
		super("No like registered for this user.");
		// TODO Auto-generated constructor stub
	}

	public NoLikeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}

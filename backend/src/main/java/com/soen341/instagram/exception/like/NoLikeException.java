package com.soen341.instagram.exception.like;

public class NoLikeException extends RuntimeException{

	public NoLikeException() {
		super("There is no like associated with this account on this post.");
		// TODO Auto-generated constructor stub
	}

	public NoLikeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}

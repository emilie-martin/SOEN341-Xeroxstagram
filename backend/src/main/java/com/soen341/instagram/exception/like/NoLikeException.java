package com.soen341.instagram.exception.like;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoLikeException extends RuntimeException {

	public NoLikeException() {
		super("There is no like associated to this content from the user.");
	}

	public NoLikeException(String message) {
		super(message);
	}
	
}

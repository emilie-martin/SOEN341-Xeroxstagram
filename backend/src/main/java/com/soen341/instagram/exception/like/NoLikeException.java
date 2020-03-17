package com.soen341.instagram.exception.like;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoLikeException extends RuntimeException
{
	public NoLikeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}

package com.soen341.instagram.exception.like;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MultipleLikeException extends RuntimeException {
	public MultipleLikeException(String msg)
	{
		super(msg);
	}
}

package com.soen341.instagram.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SamePasswordException extends RuntimeException
{
	public SamePasswordException()
	{
		super("Your new password cannot be the same as the old one");
	}

	public SamePasswordException(String message)
	{
		super(message);
	}
}

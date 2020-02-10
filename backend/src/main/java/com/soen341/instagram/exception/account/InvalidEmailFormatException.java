package com.soen341.instagram.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidEmailFormatException extends RuntimeException
{
	public InvalidEmailFormatException()
	{
		super("Invalid Email Format");
	}

	public InvalidEmailFormatException(String message)
	{
		super(message);
	}
}

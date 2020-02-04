package com.soen341.instagram.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends RuntimeException
{
	public InvalidEmailException()
	{
		super("Invalid Email Format");
	}

	public InvalidEmailException(String message)
	{
		super(message);
	}
}

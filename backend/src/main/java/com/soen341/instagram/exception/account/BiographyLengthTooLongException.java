package com.soen341.instagram.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BiographyLengthTooLongException extends RuntimeException
{
	public BiographyLengthTooLongException()
	{
		super("Biography length is too long");
	}
	
	public BiographyLengthTooLongException(String msg)
	{
		super(msg);
	}
}

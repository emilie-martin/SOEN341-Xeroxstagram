package com.soen341.instagram.exception.picture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UnknownIOException extends RuntimeException
{

	public UnknownIOException(String msg)
	{
		super(msg);
	}

	public UnknownIOException(String msg, Exception e)
	{
		super(msg, e);
	}
}

package com.soen341.instagram.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)

//Thrown when a user tries to follow their own account
public class SameAccountException extends RuntimeException
{
	public SameAccountException(String msg)
	{
		super(msg);
	}
}

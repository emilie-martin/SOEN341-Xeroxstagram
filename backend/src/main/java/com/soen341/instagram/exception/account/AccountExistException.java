package com.soen341.instagram.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountExistException extends RuntimeException
{
	public AccountExistException(String msg)
	{
		super(msg);
	}
}

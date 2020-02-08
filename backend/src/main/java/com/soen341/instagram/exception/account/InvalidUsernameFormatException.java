package com.soen341.instagram.exception.account;

public class InvalidUsernameFormatException extends RuntimeException
{
	public InvalidUsernameFormatException()
	{
		super("Username format is invalid");
	}
}

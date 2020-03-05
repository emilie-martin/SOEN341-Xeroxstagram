package com.soen341.instagram.exception.comment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Throws exception when a user tries to delete a comment that isn't their's
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnauthorizedRightsException extends RuntimeException
{
	public UnauthorizedRightsException()
	{
		super("Unauthorized Rights");
	}
}

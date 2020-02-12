package com.soen341.instagram.exception.like;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MultipleLikeException extends RuntimeException
{
	public MultipleLikeException()
	{
		super("The content can only be liked once by the user");
	}

	public MultipleLikeException(String msg)
	{
		super(msg);
	}
}

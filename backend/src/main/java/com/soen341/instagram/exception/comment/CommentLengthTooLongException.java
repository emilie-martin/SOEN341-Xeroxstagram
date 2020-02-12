package com.soen341.instagram.exception.comment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CommentLengthTooLongException extends RuntimeException
{
	public CommentLengthTooLongException()
	{
		super("Comment length too long");
	}

	public CommentLengthTooLongException(String msg)
	{
		super(msg);
	}
}

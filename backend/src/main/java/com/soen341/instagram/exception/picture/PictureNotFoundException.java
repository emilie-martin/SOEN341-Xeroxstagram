package com.soen341.instagram.exception.picture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PictureNotFoundException extends RuntimeException
{
	public PictureNotFoundException()
	{
		super("Picture not found");
	}

	public PictureNotFoundException(String msg)
	{
		super(msg);
	}
}

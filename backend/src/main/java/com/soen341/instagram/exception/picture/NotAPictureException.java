package com.soen341.instagram.exception.picture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotAPictureException extends RuntimeException {
    public NotAPictureException(String msg) {
        super(msg);
    }
}

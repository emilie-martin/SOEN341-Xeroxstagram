package com.soen341.instagram.exception.picture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String msg) {
        super(msg);
    }
}

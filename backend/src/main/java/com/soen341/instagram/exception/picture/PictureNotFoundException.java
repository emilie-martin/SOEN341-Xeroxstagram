package com.soen341.instagram.exception.picture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException(String msg) {
        super(msg);
    }
}

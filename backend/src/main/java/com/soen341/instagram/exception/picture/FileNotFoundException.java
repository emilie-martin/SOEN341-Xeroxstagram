package com.soen341.instagram.exception.picture;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String msg) {
        super(msg);
    }
}

package com.soen341.instagram.exception.account;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlreadyFollowingException extends RuntimeException {
    public AlreadyFollowingException(String msg) {
        super(msg);
    }
}

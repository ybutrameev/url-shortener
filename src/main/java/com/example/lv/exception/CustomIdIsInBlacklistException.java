package com.example.lv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Custom Id is in blacklist")
public class CustomIdIsInBlacklistException extends RuntimeException {
    public CustomIdIsInBlacklistException(String message) {
        super(message);
    }
}
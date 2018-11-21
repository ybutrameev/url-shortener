package com.example.lv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Custom Url is invalid")
public class CustomIdIsInvalidException extends RuntimeException {
    public CustomIdIsInvalidException(String message) {
        super(message);
    }
}
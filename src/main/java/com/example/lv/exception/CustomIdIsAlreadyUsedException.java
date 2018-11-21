package com.example.lv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Custom Id is already used")
public class CustomIdIsAlreadyUsedException extends RuntimeException {
    public CustomIdIsAlreadyUsedException(String message) {
        super(message);
    }
}
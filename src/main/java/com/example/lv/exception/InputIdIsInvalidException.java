package com.example.lv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Input Id is invalid")
public class InputIdIsInvalidException extends RuntimeException {
    public InputIdIsInvalidException(String message) {
        super(message);
    }
}
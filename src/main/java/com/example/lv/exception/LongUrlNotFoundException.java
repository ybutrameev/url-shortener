package com.example.lv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Long URL cannot be found")
public class LongUrlNotFoundException extends RuntimeException {
    public LongUrlNotFoundException(String message) {
        super(message);
    }
}
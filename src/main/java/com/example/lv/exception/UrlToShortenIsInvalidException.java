package com.example.lv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Input Url is invalid")
public class UrlToShortenIsInvalidException extends RuntimeException {
    public UrlToShortenIsInvalidException(String message) {
        super(message);
    }
}
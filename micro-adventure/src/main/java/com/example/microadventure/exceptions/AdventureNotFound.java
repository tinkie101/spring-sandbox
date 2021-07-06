package com.example.microadventure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Adventure not found")
public class AdventureNotFound extends RuntimeException {
    public AdventureNotFound(String message) {
        super(message);
    }
}
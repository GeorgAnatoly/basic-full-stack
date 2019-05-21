package com.example.postgresdemo.exception;

import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    @Contract(pure = true)
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

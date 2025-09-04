package com.popcorn.a2z.exception;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException{
    public AppException(String message) {
        super(message);
    }
}

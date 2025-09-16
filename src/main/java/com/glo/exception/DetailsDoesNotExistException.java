package com.glo.exception;

public class DetailsDoesNotExistException extends RuntimeException {
    public DetailsDoesNotExistException(String message) {
        super(message);
    }
}

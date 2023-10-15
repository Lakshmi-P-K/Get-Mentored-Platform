package com.nineleaps.authentication.jwt.exception;


public class DuplicateDataException extends RuntimeException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
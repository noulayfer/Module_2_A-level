package com.fedorenko.service;

public class UnableToReadDataException extends RuntimeException {
    private static final String MESSAGE = "It is impossible to read this data";
    UnableToReadDataException() {
        super(MESSAGE);
    }
}

package ru.liga.api.exceptions;

public class MissingArgsException extends RuntimeException {
    public MissingArgsException(String msg) {
        super(msg);
    }
}

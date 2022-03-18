package ru.liga.api;

public class MissingArgsException extends RuntimeException {
    public MissingArgsException(String msg) {
        super(msg);
    }
}

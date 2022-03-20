package ru.liga.api.exceptions;

public class DateTooDistantException extends RuntimeException {

    public DateTooDistantException(String err) {
        super(err);
    }

    public DateTooDistantException(Exception e) {
        super(e);
    }

}

package ru.liga.api;

import java.util.Date;

public class DateTooDistantException extends RuntimeException {

    public DateTooDistantException(String err) {
        super(err);
    }

    public DateTooDistantException(Exception e) {
        super(e);
    }

}

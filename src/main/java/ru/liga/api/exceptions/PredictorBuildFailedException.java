package ru.liga.api.exceptions;

public class PredictorBuildFailedException extends RuntimeException {

    public PredictorBuildFailedException(Exception e) {
        super(e);
    }

}

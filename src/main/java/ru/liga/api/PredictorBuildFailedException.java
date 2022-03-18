package ru.liga.api;

public class PredictorBuildFailedException extends RuntimeException {

    public PredictorBuildFailedException(Exception e) {
        super(e);
    }

}

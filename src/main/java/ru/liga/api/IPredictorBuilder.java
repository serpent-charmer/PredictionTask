package ru.liga.api;

public interface IPredictorBuilder<S, RecordProcessor, P extends IPredictor> {
    public P fromRecords(Iterable<S> records) throws PredictorBuildFailedException;
}

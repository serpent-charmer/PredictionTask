package ru.liga.api;

public interface IPredictor<X, Y> {
    public void train(X x, Y y);
    public Y predict(X x) throws PredictionException;
}

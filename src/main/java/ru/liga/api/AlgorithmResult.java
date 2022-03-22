package ru.liga.api;

import ru.liga.impl.RatesResult;

import java.util.List;

public abstract class AlgorithmResult<T> {

    private T savedResults;

    protected abstract T process(List<RatesResult> results);

    public void runAndSave(List<RatesResult> results) {
        savedResults = process(results);
    }

    public T get() {
        return savedResults;
    }
}

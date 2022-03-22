package ru.liga.api;

import ru.liga.impl.RatesResult;

import java.util.List;

public interface ResultHandler {
    public void handle(AlgorithmResult result, List<RatesResult> results);
}

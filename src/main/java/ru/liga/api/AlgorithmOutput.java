package ru.liga.api;

import ru.liga.impl.RatesResult;

import java.util.List;

public interface AlgorithmOutput<T> {
    public T output(List<RatesResult> results);
}

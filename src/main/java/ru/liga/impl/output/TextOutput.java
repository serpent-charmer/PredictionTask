package ru.liga.impl.output;

import ru.liga.api.AlgorithmOutput;
import ru.liga.impl.RatesResult;

import java.util.List;

public class TextOutput implements AlgorithmOutput<String> {
    public String output(List<RatesResult> results) {
        return results.get(0).toString();
    }
}

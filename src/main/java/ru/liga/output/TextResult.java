package ru.liga.output;

import ru.liga.api.AlgorithmResult;
import ru.liga.impl.RatesResult;

import java.util.List;

public class TextResult extends AlgorithmResult<String> {

    public String process(List<RatesResult> results) {
        return results.stream().findFirst().orElseThrow(() -> new RuntimeException("No results")).toString();
    }

}

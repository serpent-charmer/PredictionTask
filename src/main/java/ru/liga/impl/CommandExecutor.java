package ru.liga.impl;

import ru.liga.algs.Currency;
import ru.liga.api.Algorithm;
import ru.liga.api.AlgorithmResult;
import ru.liga.utils.TypeFabricParser;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CommandExecutor {

    private HashMap<Class, Consumer> consumers;

    private List<Currency> currencyList;
    private LocalDate date;
    private Period period;
    private Algorithm algorithm;
    private AlgorithmResult algorithmResult;

    public CommandExecutor() {
        consumers = new HashMap<>();
    }

    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void setAlgorithmResult(AlgorithmResult algorithmResult) {
        this.algorithmResult = algorithmResult;
    }

    public void registerResultHandler(Class<? extends AlgorithmResult> output,
                                      Consumer<? extends AlgorithmResult> consumer) {
        consumers.put(output, consumer);
    }

    public void eval() {
        LinkedList<RatesResult> results = new LinkedList<>();
        for (Currency currency : currencyList) {
            Map<LocalDate, Double> rates = TypeFabricParser.currencyRepository(currency);
            algorithm.select(rates, date, period);
            RatesResult rr = algorithm.predict(rates, date, period);
            rr.setName(currency.toString());
            results.add(rr);
        }
        algorithmResult.runAndSave(results);
        consumers.get(algorithmResult.getClass()).accept(algorithmResult);
    }

}

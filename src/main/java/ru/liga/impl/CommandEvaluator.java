package ru.liga.impl;

import ru.liga.api.Algorithm;
import ru.liga.api.AlgorithmOutput;
import ru.liga.api.DateTooDistantException;
import ru.liga.api.MissingArgsException;
import ru.liga.impl.algs.Currency;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandEvaluator {

    private HashMap<Class<?>, Object> args;

    public CommandEvaluator() {
        args = new HashMap<>();
    }

    public <T> T get(Class<T> type) {
        return type.cast(args.get(type));
    }

    public void parse(String command) {

        String[] splits = command.toLowerCase().split(" +");
        for (int i = 0; i < splits.length - 1; i++) {
            String c = splits[i];
            String next = splits[i + 1];
//            System.out.println(c + " " + next);
            if (c.equals("rate")) {
                args.put(CurrencyList.class, Currency.parseMultiple(next.split(",")));
                continue;
            }
            if (c.equals("period")) {
                args.put(Period.class, TypeFabricParser.parsePeriod(next));
                continue;
            }
            if (c.equals("alg")) {
                Algorithm type = TypeFabricParser.parseAlgorithm(next);
                args.put(Algorithm.class, type);
                continue;
            }
            if (c.equals("date")) {
                args.put(LocalDate.class, LocalDate.parse(next, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                continue;
            }
            if (c.equals("output")) {
                AlgorithmOutput type = TypeFabricParser.parseOutput(next);
                args.put(AlgorithmOutput.class, type);
                continue;
            }
        }

        String errs = Arrays.asList(CurrencyList.class, Period.class, LocalDate.class,
                        Algorithm.class, AlgorithmOutput.class)
                .stream()
                .map(c -> {
                    if (!args.keySet().contains(c)) {
                        return (String.format("Missing required field: %s", c.getSimpleName()));
                    }
                    return "";
                })
                .collect(Collectors.joining("\n"));
        if (!errs.equals(""))
            throw new MissingArgsException(String.format("\n%s", errs));

    }

    public Object run() {
        CurrencyList currencyList = get(CurrencyList.class);
        LinkedList<RatesResult> results = new LinkedList<>();
        try {
            for (Currency currency : currencyList) {
                Map<LocalDate, Double> rates = TypeFabricParser.currencyRepository(currency);
                Algorithm algorithm = get(Algorithm.class);
                LocalDate date = get(LocalDate.class);
                Period period = get(Period.class);
                algorithm.select(rates, date, period);
                RatesResult rr = algorithm.predict(rates, date, period);
                rr.setName(currency.toString());
                results.add(rr);
            }
        } catch (DateTooDistantException e) {
            throw e;
        }

        Object o = get(AlgorithmOutput.class).output(results);
        return o;
    }

}

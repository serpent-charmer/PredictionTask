package ru.liga.impl;

import ru.liga.algs.Currency;
import ru.liga.api.exceptions.MissingArgsException;
import ru.liga.utils.TypeFabricParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandParser {

    public static final String RATE = "rate";
    public static final String OUTPUT = "-output";
    public static final String DATE = "-date";
    public static final String ALGORITHM = "-algorithm";
    public static final String PERIOD = "-period";
    private static final List<String> REQUIRED_FIELDS = Arrays.asList(RATE, PERIOD, ALGORITHM, DATE, OUTPUT);

    public static CommandExecutor parse(String command) {
        Set<String> args = new HashSet<>();
        String[] splits = command.toLowerCase().split(" +");
        CommandExecutor executor = new CommandExecutor();
        for (int i = 0; i < splits.length - 1; i++) {
            String c = splits[i];
            String next = splits[i + 1];
            switch (c) {
                case RATE: {
                    executor.setCurrencyList(Currency.getByNameMultiple(next.split(",")));
                    args.add(RATE);
                    break;
                }
                case PERIOD: {
                    executor.setPeriod(TypeFabricParser.parsePeriod(next));
                    args.add(PERIOD);
                    break;
                }
                case ALGORITHM: {
                    executor.setAlgorithm(TypeFabricParser.parseAlgorithm(next));
                    args.add(ALGORITHM);
                    break;
                }
                case DATE: {
                    executor.setDate(LocalDate.parse(next, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    args.add(DATE);
                    break;
                }
                case OUTPUT: {
                    executor.setAlgorithmResult(TypeFabricParser.parseOutput(next));
                    args.add(OUTPUT);
                    break;
                }
            }
        }
        String errs = REQUIRED_FIELDS
                .stream()
                .map(a -> args.contains(a) ? null : a)
                .filter(a -> a != null)
                .map(m -> (String.format("Missing required field: %s", m)))
                .collect(Collectors.joining("\n"));
        if (!errs.equals(""))
            throw new MissingArgsException(String.format("\n%s", errs));
        return executor;
    }


}

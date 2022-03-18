package ru.liga;

import ru.liga.impl.CommandEvaluator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class App {

    public static void printHelp() {
        System.out.println("tickpredict [t] [c]");
        System.out.println("where");
        System.out.println("    t: <tomorrow|week>");
        System.out.println("    c: <TRY|USD|EUR>");
        System.exit(1);
    }

    public static void main(String... args) {
        String command = Arrays.stream(args).collect(Collectors.joining(" "));
        CommandEvaluator commandEvaluator = new CommandEvaluator();
        commandEvaluator
                .parse(command);
        commandEvaluator.run();

    }
}

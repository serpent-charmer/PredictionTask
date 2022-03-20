package ru.liga;

import ru.liga.api.exceptions.MissingArgsException;
import ru.liga.impl.CommandParser;
import ru.liga.impl.CommandExecutor;
import ru.liga.output.TextResult;

import java.util.Arrays;
import java.util.stream.Collectors;

public class App {

    private static void printHelpMessage() {
        String help = "rate <USD|TRY|BGN|EUR>\n" +
                "-alg <mystic|contemp|linear>\n" +
                "-period <month|week>\n" +
                "-output <graph|text>\n";
        System.out.println(help);
    }

    public static void main(String... args) {
        String command = Arrays.stream(args).collect(Collectors.joining(" "));
        try {
            CommandExecutor executor = CommandParser
                    .parse(command);
            executor.registerResultHandler(TextResult.class,
                    System.out::println);
            executor.eval();
        } catch (MissingArgsException e) {
            printHelpMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package ru.liga.impl;

import ru.liga.api.Algorithm;
import ru.liga.api.AlgorithmOutput;
import ru.liga.impl.algs.ContemporaryAlgorithm;
import ru.liga.impl.algs.Currency;
import ru.liga.impl.algs.LinearRegressionAlgorithm;
import ru.liga.impl.algs.MysticalAlgorithm;
import ru.liga.impl.output.GraphOutput;
import ru.liga.impl.output.TextOutput;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

public class TypeFabricParser {

    public static Algorithm parseAlgorithm(String s) {
        switch(s) {
            case "mystic":
                return new MysticalAlgorithm();
            case "contemp":
                return new ContemporaryAlgorithm();
            case "linear":
                return new LinearRegressionAlgorithm();
        }
        throw new RuntimeException("No such algorithm: " + s);
    }


    public static AlgorithmOutput parseOutput(String s) {
        switch(s) {
            case "graph": return new GraphOutput();
            case "text": new TextOutput();
        }
        throw new RuntimeException("No such output: " + s);
    }

    public static Period parsePeriod(String s) {
        switch(s) {
            case "week": return Period.ofDays(7);
            case "month": return Period.ofDays(30);
        }
        throw new RuntimeException("No such period: " + s);
    }

    public static Map<LocalDate, Double> currencyRepository(Currency currency) {
        CSVRepository pFactory = CSVRepository.getCSVPredictorFactory();
        switch(currency) {
            case EUR: {
                return pFactory.getMapFromCsvFile("data/EUR_F01_02_2005_T05_03_2022.csv");
            }
            case BGN: {
                return pFactory.getMapFromCsvFile("data/BGN_F01_02_2005_T05_03_2022.csv");
            }
            case TRY: {
                return pFactory.getMapFromCsvFile("data/TRY_F01_02_2005_T05_03_2022.csv");
            }
            case USD: {
                return pFactory.getMapFromCsvFile("data/USD_F01_02_2005_T05_03_2022.csv");
            }
            default: return null;
        }
    }

}

package ru.liga.utils;

import ru.liga.api.Algorithm;
import ru.liga.api.AlgorithmResult;
import ru.liga.algs.ContemporaryAlgorithm;
import ru.liga.algs.Currency;
import ru.liga.algs.LinearRegressionAlgorithm;
import ru.liga.algs.MysticalAlgorithm;
import ru.liga.output.GraphResult;
import ru.liga.output.TextResult;
import ru.liga.repository.CSVRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

public class TypeFabricParser {

    public static final String WEEK = "week";
    public static final String MONTH = "month";
    public static final String GRAPH = "graph";
    public static final String TEXT = "text";
    public static final String MYSTIC = "mystic";
    public static final String CONTEMP = "contemp";
    public static final String LINEAR = "linear";

    public static Algorithm parseAlgorithm(String s) {
        switch(s) {
            case MYSTIC:
                return new MysticalAlgorithm();
            case CONTEMP:
                return new ContemporaryAlgorithm();
            case LINEAR:
                return new LinearRegressionAlgorithm();
        }
        throw new RuntimeException("No such algorithm: " + s);
    }


    public static AlgorithmResult parseOutput(String s) {
        switch(s) {
            case GRAPH: return new GraphResult();
            case TEXT: new TextResult();
        }
        throw new RuntimeException("No such output: " + s);
    }

    public static Period parsePeriod(String s) {
        switch(s) {
            case WEEK: return Period.ofDays(7);
            case MONTH: return Period.ofDays(30);
        }
        throw new RuntimeException("No such period: " + s);
    }

    public static Map<LocalDate, Double> currencyRepository(Currency currency) {
        CSVRepository pFactory = CSVRepository.getCSVRepository();
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

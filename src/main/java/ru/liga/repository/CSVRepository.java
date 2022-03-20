package ru.liga.repository;

import org.apache.commons.csv.CSVFormat;
import ru.liga.api.exceptions.PredictorBuildFailedException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVRepository {

    public static final String DATA = "data";
    public static final String CURS = "curs";
    public static final String NOMINAL = "nominal";
    public static final String CDX = "cdx";
    private CSVFormat format;
    private DateTimeFormatter dateTimeFormatter;

    public CSVRepository() {
        format = CSVFormat.DEFAULT
                .builder()
                .setHeader(NOMINAL, DATA, CURS, CDX)
                .setSkipHeaderRecord(true)
                .setDelimiter(";")
                .build();
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    public Map<LocalDate, Double> getMapFromCsvFile(String path) {
        try {
            return format.parse(new FileReader(path))
                    .stream()
                    .collect(Collectors
                            .toMap(v -> LocalDate.parse(v.get(DATA), dateTimeFormatter),
                                    v -> Double.valueOf(v.get(CURS).replace(",", "."))));
        } catch (IOException e) {
            throw new PredictorBuildFailedException(e);
        }
    }

    public static CSVRepository getCSVRepository() {
        return new CSVRepository();
    }

}

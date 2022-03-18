package ru.liga.impl;

import org.apache.commons.csv.CSVRecord;
import ru.liga.api.PredictorBuildFailedException;
import ru.liga.api.ProcessingException;

import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CSVRepository {

    private CSVDataSource source;
    private CSVDataProcessor processor;

    public CSVRepository(CSVDataSource source, CSVDataProcessor processor) {
        this.source = source;
        this.processor = processor;
    }

    public static CSVRepository getPredictorFactory(CSVDataSource source, CSVDataProcessor processor) {
        return new CSVRepository(source, processor);
    }

    public static CSVRepository getCSVPredictorFactory() {
        CSVDataSource source = new CSVDataSource();
//        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);

        CSVRecordReader recordReader = new CSVRecordReader(formatter, numberFormat);
        CSVDataProcessor processor = new CSVDataProcessor(recordReader);

        return new CSVRepository(source, processor);
    }

    public Map<LocalDate, Double> getMapFromCsvFile(String path) throws PredictorBuildFailedException {
        try {
            Iterable<CSVRecord> recs = source.getAll(new FileReader(path));
            List<Map.Entry<LocalDate, Double>> records = processor.getAll(recs);
            Map<LocalDate, Double> map = records.stream()
                    .collect(Collectors.toMap(v -> v.getKey(), v -> v.getValue()));
            return map;
        } catch (IOException | ProcessingException e) {
            throw new PredictorBuildFailedException(e);
        }
    }


}

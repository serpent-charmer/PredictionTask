package ru.liga.impl;

import org.apache.commons.csv.CSVRecord;
import ru.liga.api.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class PredictorFactory implements IPredictorBuilder<
        Map.Entry<Long, Double>,
        CSVDataProcessor,
        LastSevenPredictor> {

    private CSVDataSource source;
    private CSVDataProcessor processor;

    public PredictorFactory(CSVDataSource source, CSVDataProcessor processor) {
        this.source = source;
        this.processor = processor;
    }

    public static PredictorFactory getPredictorFactory(CSVDataSource source, CSVDataProcessor processor) {
        return new PredictorFactory(source, processor);
    }

    public static PredictorFactory getCSVPredictorFactory() {
        CSVDataSource source = new CSVDataSource();
        DateFormat dateParser = new SimpleDateFormat("dd.MM.yyyy");
        NumberFormat numberParser = NumberFormat.getInstance(Locale.FRANCE);

        CSVRecordReader recordReader = new CSVRecordReader(dateParser, numberParser);
        CSVDataProcessor processor = new CSVDataProcessor(recordReader);

        return new PredictorFactory(source, processor);
    }

    public LastSevenPredictor fromCsvFile(String path) throws PredictorBuildFailedException {
        try {
            Iterable<CSVRecord> recs = source.getAll(new FileReader(new File(path)));
            Iterable<Map.Entry<Long, Double>> records = processor.getAll(recs);
            return fromRecords(records);
        } catch (IOException|ProcessingException e) {
            throw new PredictorBuildFailedException(e);
        }
    }

    @Override
    public LastSevenPredictor fromRecords(Iterable<Map.Entry<Long, Double>> records) throws PredictorBuildFailedException {
            LastSevenPredictor predictor = new LastSevenPredictor();
            for (Map.Entry<Long, Double> record : records) {
                long date = record.getKey();
                double tickRate = record.getValue();
                predictor.train(date, tickRate);
            }
            return predictor;
    }
}

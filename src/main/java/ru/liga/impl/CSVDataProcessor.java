package ru.liga.impl;

import org.apache.commons.csv.CSVRecord;
import ru.liga.api.IDataProcessor;
import ru.liga.api.ProcessingException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

public class CSVDataProcessor implements IDataProcessor<
        CSVRecord,
        Map.Entry<LocalDate, Double>> {

    private CSVRecordReader parser;

    public CSVDataProcessor(CSVRecordReader parser) {
        this.parser = parser;
    }

    @Override
    public List<Map.Entry<LocalDate, Double>> getAll(Iterable<CSVRecord> records) throws ProcessingException {

        ArrayList<Map.Entry<LocalDate, Double>> recs = new ArrayList<>();

        try {
            for (CSVRecord record : records) {
                Map.Entry<LocalDate, Double> pair = parser.parse(record);
                LocalDate date = pair.getKey();
                double tick = pair.getValue();
                recs.add(new AbstractMap.SimpleEntry<>(date, tick));
            }
            return recs;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ProcessingException();
        }
    }

}

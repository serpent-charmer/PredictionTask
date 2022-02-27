package ru.liga.impl;

import org.apache.commons.csv.CSVRecord;
import ru.liga.api.IDataProcessor;
import ru.liga.api.ProcessingException;

import java.text.ParseException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;

public class CSVDataProcessor implements IDataProcessor<
        CSVRecord,
        Map.Entry<Long, Double>> {

    private CSVRecordReader parser;

    public CSVDataProcessor(CSVRecordReader parser) {
        this.parser = parser;
    }

    @Override
    public Iterable<Map.Entry<Long, Double>> getAll(Iterable<CSVRecord> records) throws ProcessingException {

        ArrayList<Map.Entry<Long, Double>> recs = new ArrayList<>();

        try {
            for (CSVRecord record : records) {
                Map.Entry<Long, Double> pair = parser.parse(record);
                long date = pair.getKey();
                double tick = pair.getValue();
                recs.add(new AbstractMap.SimpleEntry<Long, Double>(date, tick));
            }
            return recs;
        } catch (ParseException e) {
            throw new ProcessingException();
        }
    }



}

package ru.liga.impl;

import org.apache.commons.csv.CSVRecord;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.AbstractMap;
import java.util.Map;

public class CSVRecordReader {

    private DateFormat dateParser;
    private NumberFormat numberParser;

    public CSVRecordReader(DateFormat dateParser,
                           NumberFormat numberParser) {
        this.dateParser = dateParser;
        this.numberParser = numberParser;
    }

    public long parseDate(String date) throws ParseException {
        long time = dateParser.parse(date).getTime();
        return time;
    }

    public double parseTick(String tickRate) throws ParseException {
        Number number = numberParser.parse(tickRate);
        return number.doubleValue();
    }

    public Map.Entry<Long, Double> parse(CSVRecord record) throws ParseException {
        String date = record.get("data");
        String tickRate = record.get("curs");
        return new AbstractMap.SimpleEntry<Long, Double>(
                parseDate(date),
                parseTick(tickRate));
    }
}
package ru.liga.impl;

import org.apache.commons.csv.CSVRecord;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;

public class CSVRecordReader {

    private DateTimeFormatter dateParser;
    private NumberFormat numberParser;

    public CSVRecordReader(DateTimeFormatter dateParser,
                           NumberFormat numberParser) {
        this.dateParser = dateParser;
        this.numberParser = numberParser;
    }

    public LocalDate parseDate(String date) throws ParseException {
        return LocalDate.parse(date, dateParser);
    }

    public double parseTick(String tickRate) throws ParseException {
        Number number = numberParser.parse(tickRate);
        return number.doubleValue();
    }

    public Map.Entry<LocalDate, Double> parse(CSVRecord record) throws ParseException {
        String date = record.get("data");
        String tickRate = record.get("curs");
        return new AbstractMap.SimpleEntry<>(
                parseDate(date),
                parseTick(tickRate));
    }
}
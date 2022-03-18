package ru.liga.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.liga.api.IDataSource;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CSVDataSource implements IDataSource<CSVRecord> {

    private CSVFormat format;
    private List<CSVRecord> records;

    public CSVDataSource() {
        format = CSVFormat.DEFAULT
                .builder()
                .setHeader("nominal", "data", "curs", "cdx")
                .setSkipHeaderRecord(true)
                .setDelimiter(";")
                .build();
    }

    @Override
    public Iterable<CSVRecord> getAll(Reader reader) throws IOException {
        return format.parse(reader);
    }
}

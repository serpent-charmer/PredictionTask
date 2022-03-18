package ru.liga.api;

import java.util.List;

public interface IDataProcessor<RecordType, OutputType> {
    List<OutputType> getAll(Iterable<RecordType> records) throws ProcessingException;
}

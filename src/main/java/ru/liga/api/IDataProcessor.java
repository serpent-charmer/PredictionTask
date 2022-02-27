package ru.liga.api;

public interface IDataProcessor<RecordType, OutputType> {
    Iterable<OutputType> getAll(Iterable<RecordType> records) throws ProcessingException;
}

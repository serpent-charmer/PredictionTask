package ru.liga.api;

import java.io.IOException;
import java.io.Reader;

public interface IDataSource<Data> {
    public Iterable<Data> getAll(Reader reader) throws IOException;
}

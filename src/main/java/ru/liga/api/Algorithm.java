package ru.liga.api;

import ru.liga.impl.RatesResult;

import java.time.LocalDate;
import java.time.Period;
import java.util.Map;

public interface Algorithm {
    public void select(Map<LocalDate, Double> rates, LocalDate date, Period period);
    public RatesResult predict(Map<LocalDate, Double> rates, LocalDate date, Period period);
}

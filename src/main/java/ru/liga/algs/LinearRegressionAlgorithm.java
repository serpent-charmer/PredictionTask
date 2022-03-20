package ru.liga.algs;

import ru.liga.api.Algorithm;
import ru.liga.utils.DateUtils;
import ru.liga.impl.RatesResult;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinearRegressionAlgorithm implements Algorithm {

    private LinearRegression linearRegression;

    public LinearRegressionAlgorithm() {
    }

    public void select(Map<LocalDate, Double> rates, LocalDate date, Period period) {
        Map<LocalDate, Double> monthRates = DateUtils.getMonth(rates, date);
        double[] x = new double[monthRates.size()];
        double[] y = new double[monthRates.size()];
        Iterator<Map.Entry<LocalDate, Double>> iterator = monthRates.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<LocalDate, Double> entry = iterator.next();
            x[i] = entry.getKey().toEpochDay();
            y[i] = entry.getValue();
            ++i;
        }
        linearRegression = new LinearRegression(x, y);
    }

    public RatesResult predict(Map<LocalDate, Double> rates, LocalDate date, Period period) {
        List<Date> dates = new ArrayList<>();
        List<Double> newRates = Stream
                .iterate(date, d -> d.plusDays(1))
                .limit(period.getDays())
                .peek(d -> dates.add(Date.from(d.atStartOfDay(
                        ZoneId.systemDefault()).toInstant())))
                .map(d -> linearRegression.predict(d.toEpochDay()))
                .collect(Collectors.toList());
        return new RatesResult("LinearRegression", dates, newRates);
    }

}

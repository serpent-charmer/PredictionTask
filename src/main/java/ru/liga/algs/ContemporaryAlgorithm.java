package ru.liga.algs;

import ru.liga.api.Algorithm;
import ru.liga.utils.DateUtils;
import ru.liga.impl.RatesResult;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContemporaryAlgorithm implements Algorithm {


    public ContemporaryAlgorithm() {

    }

    public void select(Map<LocalDate, Double> rates, LocalDate date, Period period) {

    }

    public RatesResult predict(Map<LocalDate, Double> rates, LocalDate date, Period period) {
        ArrayList<Date> dates = new ArrayList<>();
        List<Double> newRates = Stream.
                iterate(date, d -> d.plusDays(1))
                .limit(period.getDays())
                .map(d -> {
                    dates.add(Date.from(d.atStartOfDay(
                            ZoneId.systemDefault()).toInstant()));
                    return DateUtils.threeYears(rates, d);
                })
                .collect(Collectors.toList());

        return new RatesResult("Contemporary", dates, newRates);
    }

}

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

public class MysticalAlgorithm implements Algorithm {

    private Random random;

    public MysticalAlgorithm() {
        random = new Random();
    }

    public void select(Map<LocalDate, Double> rates, LocalDate date, Period period) {

    }

    public RatesResult predict(Map<LocalDate, Double> rates, LocalDate date, Period period) {

        List<LocalDate> moons = DateUtils.getThreeFullMoons(date);
        double sum = 0;
        for (LocalDate moon : moons) {
            Double rate = DateUtils.findClosestBackwards(rates, moon);
            sum += rate;
        }

        double avg = sum / 3;

        double avgTenthPercent = avg * 1 / 10;
        List<Double> doubles = random.doubles(avg - avgTenthPercent,
                        avg + avgTenthPercent)
                .limit(period.getDays())
                .boxed()
                .collect(Collectors.toList());

        List<Date> dates = Stream.
                iterate(date, d -> d.plusDays(1))
                .limit(period.getDays())
                .map(d -> Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());

        return new RatesResult("Mystical", dates, doubles);
    }

}

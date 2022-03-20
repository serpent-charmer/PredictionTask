package ru.liga.utils;

import org.shredzone.commons.suncalc.MoonPhase;
import ru.liga.api.exceptions.DateTooDistantException;

import java.time.LocalDate;
import java.util.*;

public class DateUtils {

    public static List<LocalDate> getThreeFullMoons(LocalDate date) {

        ArrayList<LocalDate> moonPhases = new ArrayList<>();
        MoonPhase.Parameters parameters = MoonPhase.compute()
                .phase(MoonPhase.Phase.FULL_MOON);

        LocalDate iter = LocalDate.from(date);

        MoonPhase moonPhase = parameters
                .on(iter)
                .execute();

        LocalDate nextFullMoon = moonPhase
                .getTime()
                .toLocalDate();
        LocalDate prevFullMoon;

        while (nextFullMoon.compareTo(date) > 0) {
            iter = iter.minusDays(1);
            moonPhase = parameters
                    .on(iter)
                    .execute();
            nextFullMoon = moonPhase
                    .getTime()
                    .toLocalDate();
        }

        while (moonPhases.size() < 3) {
            iter = iter.minusDays(1);
            moonPhase = parameters
                    .on(iter)
                    .execute();
            prevFullMoon = LocalDate.from(nextFullMoon);
            nextFullMoon = moonPhase
                    .getTime()
                    .toLocalDate();
            if (!nextFullMoon.equals(prevFullMoon)) {
                moonPhases.add(prevFullMoon);
            }
        }

        return moonPhases;
    }

    public static Map<LocalDate, Double> getMonth(Map<LocalDate, Double> rates, LocalDate date) {
        HashMap<LocalDate, Double> map = new HashMap<>();
        LocalDate current = LocalDate.from(date);
        current = current.minusDays(30);
        Double rate = DateUtils.findClosestBackwards(rates, date);
        for (int i = 0; i < 30; i++) {
            map.put(current, rate);
            current = current.plusDays(1);
            Double tmp = rates.get(current);
            if (tmp != null) {
                rate = tmp;
            }
        }
        return map;
    }

    public static Double threeYears(Map<LocalDate, Double> dates, LocalDate date) {
        LocalDate current = LocalDate.from(date);
        Double first = findClosestBackwards(dates, current.minusYears(2));
        Double second = findClosestBackwards(dates, current.minusYears(3));
        return first+second;
    }

    public static Double findClosestBackwards(Map<LocalDate, Double> dates, LocalDate date, int maxDiff) {
        LocalDate current = LocalDate.from(date);
        Double rs = dates.get(date);

        int backwardsLength = 0;

        while (rs == null && ++backwardsLength < maxDiff) {
            current = current.minusDays(1);
            rs = dates.get(current);
        }

        if(rs == null) {
            throw new DateTooDistantException("Date is too far in future");
        }

        return rs;
    }

    public static Double findClosestBackwards(Map<LocalDate, Double> dates, LocalDate date) {
        return findClosestBackwards(dates, date, 30);
    }

}

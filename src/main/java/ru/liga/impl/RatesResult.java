package ru.liga.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RatesResult {

    private String name;
    private final List<Date> dates;
    private final List<Double> rates;

    private final SimpleDateFormat format;

    public RatesResult(String name, List<Date> dates, List<Double> rates) {
        this.name = name;
        this.dates = dates;
        this.rates = rates;
        format = new SimpleDateFormat("EEE dd.MM.yyyy");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Date> getDates() {
        return dates;
    }

    public List<Double> getRates() {
        return rates;
    }

    public String toString() {
        String rs = String.format("%s\n", name);
        for (int i = 0; i < dates.size(); i++) {
            Date date = dates.get(i);
            rs += String.format("%s - %.2f\n", format.format(date), rates.get(i));
        }
        return rs.trim();
    }


}

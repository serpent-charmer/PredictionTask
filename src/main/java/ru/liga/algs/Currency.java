package ru.liga.algs;

import java.util.ArrayList;
import java.util.List;

public enum Currency {

    USD,
    EUR,
    TRY,
    BGN;

    public static Currency getByName(String name) {
        for(Currency c : Currency.values()) {
            if(c.name().toLowerCase().equals(name)) {
                return c;
            }
        }
        throw new RuntimeException(String.format("no such currency: %s", name.toUpperCase()));
    }

    public static List<Currency> getByNameMultiple(String[] names) {
        List<Currency> currencies = new ArrayList<>();
        for (String s : names) {
            currencies.add(getByName(s));
        }
        return currencies;
    }

}

package ru.liga.impl.algs;

import ru.liga.impl.CurrencyList;

import java.util.ArrayList;
import java.util.List;

public enum Currency {

    USD,
    EUR,
    TRY,
    BGN;

    public static Currency parse(String name) {
        switch(name) {
            case "usd": return USD;
            case "eur": return EUR;
            case "try": return TRY;
            case "bgn": return BGN;
            default: return null;
        }
    }

    public static List<Currency> parseMultiple(String[] names) {
        CurrencyList currencies = new CurrencyList();
        for (int i = 0; i < names.length; i++) {
            currencies.add(parse(names[i]));
        }
        return currencies;
    }

}

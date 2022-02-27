package ru.liga.impl;

import ru.liga.api.IPredictor;
import ru.liga.api.PredictionException;

import java.util.ArrayList;

public class LastSevenPredictor implements IPredictor<Long, Double> {

    private ArrayList<Double> ticks;

    public LastSevenPredictor() {
        ticks = new ArrayList<>();
    }

    @Override
    public void train(Long date, Double tick) {
        ticks.add(ticks.size(), tick);
    }

    @Override
    public Double predict(Long date) throws PredictionException {

        int grab = Math.min(ticks.size()-1, 7);

        double avg = 0.0;

        for (int i = 0; i < grab; i++) {
            double tick = ticks.get(i);
            avg += tick;
        }

        return avg / grab;
    }

}

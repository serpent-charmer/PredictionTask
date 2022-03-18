package ru.liga.impl.algs;

import ru.liga.api.PredictionException;

import java.util.ArrayList;

public class LastSevenPredictor {

    private ArrayList<Double> ticks;

    public LastSevenPredictor(ArrayList<Double> ticks) {
        this.ticks = ticks;
    }

    public Double predict() {

        int grab = Math.min(ticks.size() - 1, 7);

        double avg = 0.0;

        for (int i = 0; i < grab; i++) {
            double tick = ticks.get(i);
            avg += tick;
        }

        return avg / grab;
    }

}

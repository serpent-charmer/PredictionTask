package ru.liga;

import ru.liga.api.IPredictor;
import ru.liga.api.PredictionException;
import ru.liga.api.PredictorBuildFailedException;
import ru.liga.impl.*;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Function;

public class App {

    public static List<Double> predictWeek(IPredictor<Long, Double> predictor) throws PredictionException {
        ArrayList<Double> ticks = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            double tick = predictor.predict(null);
            ticks.add(tick);
            predictor.train(null, tick);
        }

        return ticks;
    }

    public static List<Double> predictTomorrow(IPredictor<Long, Double> predictor) throws PredictionException  {
        ArrayList<Double> list = new ArrayList<>();
        list.add(predictor.predict(null));
        return list;
    }

    public static void printHelp() {
        System.out.println("tickpredict [t] [c]");
        System.out.println("where");
        System.out.println("    t: <tomorrow|week>");
        System.out.println("    c: <TRY|USD|EUR>");
        System.exit(1);
    }

    public static void main(String[] args) {

        if(args.length < 2) {
            printHelp();
        }

        if(args[0] == "--help") {
            printHelp();
        }

        HashMap<String, IPredictor<Long, Double>> predictors = new HashMap<>();
        HashMap<String, Function<IPredictor<Long, Double>, List<Double>>>
                predictionTypes = new HashMap<>();

        predictionTypes.put("tomorrow", (p) -> {
            try {
                return predictTomorrow(p);
            } catch (PredictionException e) {
                e.printStackTrace();
            }
            return null;
        });

        predictionTypes.put("week", (p) -> {
            try {
                return predictWeek(p);
            } catch (PredictionException e) {
                e.printStackTrace();
            }
            return null;
        });

        PredictorFactory pFactory = PredictorFactory.getCSVPredictorFactory();

        try {
            LastSevenPredictor usdPredictor = pFactory
                    .fromCsvFile("data/USD_F01_02_2002_T01_02_2022.csv");

            LastSevenPredictor tryPredictor = pFactory
                    .fromCsvFile("data/TRY_F01_02_2002_T01_02_2022.csv");

            LastSevenPredictor eurPredictor = pFactory
                    .fromCsvFile("data/EUR_F01_02_2002_T01_02_2022.csv");

            predictors.put("USD", usdPredictor);
            predictors.put("TRY", tryPredictor);
            predictors.put("EUR", eurPredictor);

        } catch (PredictorBuildFailedException e) {
//            e.printStackTrace();
            System.out.println("Отсутствуют/Неполные данные для построения прогноза");
            System.exit(1);
        }

        String predictionType = args[0];
        String currency = args[1];

        IPredictor<Long, Double> predictor = predictors.get(currency);

        if(predictor == null) {
            System.out.println("Возможные типы валют: " + currency);
            System.exit(1);
        }

        Function<IPredictor<Long, Double>, List<Double>>
                predictionFunction = predictionTypes.get(predictionType);

        if(predictionFunction == null) {
            System.out.println("Возможные типы прогноза: " + predictionType);
            System.exit(1);
        }

        Collection<Double> ticks = predictionFunction.apply(predictor);
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (Double tick :
                ticks) {
            String dateStr = date.format(formatter);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String weekDayStr = dayOfWeek.getDisplayName(TextStyle.SHORT, new Locale("ru"));
            System.out.printf("%s %s: %.2f\n", weekDayStr, dateStr, tick);
            date = date.plusDays(1);
        }
    }
}

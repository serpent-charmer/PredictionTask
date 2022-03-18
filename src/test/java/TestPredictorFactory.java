import org.junit.Test;
import ru.liga.api.PredictionException;
import ru.liga.api.PredictorBuildFailedException;
import ru.liga.impl.*;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.assertTrue;

public class TestPredictorFactory {


    @Test()
    public void noDataFile() throws PredictorBuildFailedException {
        System.out.println("HELLO!");
//        CSVDataSource source = null;
//        CSVDataProcessor processor = null;
//
//        CSVPredictorFactory pFactory = CSVPredictorFactory.getPredictorFactory(source, processor);
//
//        LastSevenPredictor usdPredictor = pFactory
//                .fromCsvFile("nonexistent_file.csv");

    }

    @Test()
    public void usdPredictTest() throws PredictorBuildFailedException, PredictionException {


        assertTrue(false);

//        CSVDataSource source = new CSVDataSource();
//        DateFormat dateParser = new SimpleDateFormat("dd.MM.yyyy");
//        NumberFormat numberParser = NumberFormat.getInstance(Locale.FRANCE);
//
//        CSVRecordReader recordReader = new CSVRecordReader(dateParser, numberParser);
//        CSVDataProcessor processor = new CSVDataProcessor(recordReader);
//        CSVPredictorFactory pFactory = CSVPredictorFactory.getPredictorFactory(source, processor);
//
//
//        LastSevenPredictor usdPredictor = pFactory
//                .fromCsvFile("data/USD_F01_02_2002_T01_02_2022.csv");
//
//        double rs = usdPredictor.predict(null);
//        double expected = 78.19756666666667;
//        org.junit.Assert.assertEquals(rs, expected, 0.1);

    }



}

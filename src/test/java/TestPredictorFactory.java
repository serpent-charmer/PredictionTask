import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import ru.liga.api.IDataProcessor;
import ru.liga.api.IDataSource;
import ru.liga.api.PredictionException;
import ru.liga.api.PredictorBuildFailedException;
import ru.liga.impl.*;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

public class TestPredictorFactory {


    @Test(expected = PredictorBuildFailedException.class)
    public void noDataFile() throws PredictorBuildFailedException {

        CSVDataSource source = null;
        CSVDataProcessor processor = null;

        PredictorFactory pFactory = PredictorFactory.getPredictorFactory(source, processor);

        LastSevenPredictor usdPredictor = pFactory
                .fromCsvFile("nonexistent_file.csv");

    }

    @Test()
    public void usdPredictTest() throws PredictorBuildFailedException, PredictionException {

        CSVDataSource source = new CSVDataSource();
        DateFormat dateParser = new SimpleDateFormat("dd.MM.yyyy");
        NumberFormat numberParser = NumberFormat.getInstance(Locale.FRANCE);

        CSVRecordReader recordReader = new CSVRecordReader(dateParser, numberParser);
        CSVDataProcessor processor = new CSVDataProcessor(recordReader);
        PredictorFactory pFactory = PredictorFactory.getPredictorFactory(source, processor);


        LastSevenPredictor usdPredictor = pFactory
                .fromCsvFile("data/USD_F01_02_2002_T01_02_2022.csv");

        double rs = usdPredictor.predict(null);
        double expected = 78.19756666666667;
        org.junit.Assert.assertEquals(rs, expected, 0.1);

    }



}

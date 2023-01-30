package by.nhorushko.distancecalculator;

import by.nhorushko.util.MessageFileReader;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DistanceCalculatorEtalonTest {
    private static final String PATH = "src/test/resources/tracks/etalon";

    DistanceCalculator calculator = new DistanceCalculatorImpl();

    @Test
    public void check_11429() {
        double etalon = 11.4296;
        assertMileage(etalon, 11.477, calculate("track-11429-meters-5723.csv"));
        assertMileage(etalon, 11.465, calculate("track-11429-meters-5724.csv"));
        assertMileage(etalon, 11.436, calculate("track-11429-meters-5725.csv"));
    }

    @Test
    public void check_8280() {
        double etalon = 8.280698;
        assertMileage(etalon, 8.364, calculate("track-8280-meters-5723.csv"));
        assertMileage(etalon, 8.349, calculate("track-8280-meters-5724.csv"));
        assertMileage(etalon, 8.186, calculate("track-8280-meters-5725.csv"));
    }

    @Test
    public void check_72640() {
        double etalon = 72.640508;
        assertMileage(etalon, 72.876, calculate("track-72640-meters-5723.csv"));
        assertMileage(etalon, 72.836, calculate("track-72640-meters-5724.csv"));
        assertMileage(etalon, 72.868, calculate("track-72640-meters-5725.csv"));
    }

    @Test
    public void check_7541() {
        double etalon = 7.541945;
        assertMileage(etalon, 7.438, calculate("track-7541-meters-5723.csv"));
        assertMileage(etalon, 7.476, calculate("track-7541-meters-5724.csv"));
        assertMileage(etalon, 7.486, calculate("track-7541-meters-5725.csv"));
    }

    @Test
    public void check_12018() {
        double etalon = 12.018727;
        assertMileage(etalon, 11.982, calculate("track-12018-meters-5723.csv"));
        assertMileage(etalon, 12.057, calculate("track-12018-meters-5724.csv"));
        assertMileage(etalon, 12.061, calculate("track-12018-meters-5725.csv"));
    }



    private void assertMileage(double etalon, double expected, double actual) {
        assertWithEpsilon(etalon, actual);
        assertEquals(expected, actual, 0.001);
    }

    private void assertWithEpsilon(double expected, double actual) {
        assertEquals("etalon value big difference", expected, actual, expected * 0.016);
    }

    private double calculate(String fileName) {
        List<LatLngAlt> coordinates = MessageFileReader.read(PATH + "/" + fileName);
        return calculator.calculateDistance(coordinates, DistanceCalculatorSettingsImpl.defaultValue());
    }
}

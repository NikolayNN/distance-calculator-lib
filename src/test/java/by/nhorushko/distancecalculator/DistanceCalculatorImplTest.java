package by.nhorushko.distancecalculator;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class DistanceCalculatorImplTest {

    private static final String TRACK_795523_FILE = "src/test/resources/tracks/track795523";
    private static final String TRACK_79206_FILE = "src/test/resources/tracks/track79206";
    private static final String TRACK_366510_FILE = "src/test/resources/tracks/track366510";
    private static final String TRACK_79206_FILE_ZEROS = "src/test/resources/tracks/track79206-with-zeros-coordinates";
    private DistanceCalculator distanceCalculator;

    @Before
    public void setUp() {
        distanceCalculator = new DistanceCalculatorImpl();
    }

    @Test
    public void isFileExist() {

        File file = new File(TRACK_79206_FILE);
        assertTrue(file.exists());
        file = new File(TRACK_795523_FILE);
        assertTrue(file.exists());
        file = new File(TRACK_366510_FILE);
        assertTrue(file.exists());
        file = new File(TRACK_79206_FILE_ZEROS);
        assertTrue(file.exists());
    }

    @Test
    public void calculateDistance() {

        //given
        final double expectedMileage = 79.20731164889823;
        List<LatLngAlt> coordinate = readCoordinatesFromFile(TRACK_79206_FILE);
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        //then
        assertEquals(expectedMileage, actual, 0.00001);
    }

    @Test
    public void calculateDistance2() {

        //given
        double expected = 366.51252783401156;
        List<LatLngAlt> coordinate = readCoordinatesFromFile(TRACK_366510_FILE);
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        //then
        assertEquals(expected, actual, 0.00001);
    }

    @Test
    public void calculateDistance3() {

        //given
        double expected = 795.529757461319;
        List<LatLngAlt> coordinate = readCoordinatesFromFile(TRACK_795523_FILE);
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        //then
        assertEquals(expected, actual, 0.00001);
    }

    @Test
    public void calculateDistanceIfHasZerosCoordinates() {

        //given
        double expected = 78.77358322296419;
        List<LatLngAlt> coordinate = readCoordinatesFromFile(TRACK_79206_FILE_ZEROS);
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        //then
        assertEquals(expected, actual, 0.00001);
    }

    @Test
    public void calculateDistance_ok() {
        //given
        List<LatLngAlt> coordinate = List.of(
                new LatLngAltImpl(54.27379608154297f, 25.376237869262695f, 169, 0, true),
                new LatLngAltImpl(54.27385711669922f, 25.37613296508789f, 167, 7, true),
                new LatLngAltImpl(54.27388381958008f, 25.37611198425293f, 167, 0, true)
        );
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        assertEquals(0.013087874445824278, actual, 0.00001);
    }

    @Test
    public void calculateDistance_hasNotValid_ok() {
        //given
        List<LatLngAlt> coordinate = List.of(
                new LatLngAltImpl(54.27379608154297f, 25.376237869262695f, 169, 0, false),
                new LatLngAltImpl(54.27385711669922f, 25.37613296508789f, 167, 7, true),
                new LatLngAltImpl(54.27388381958008f, 25.37611198425293f, 167, 0, true)
        );
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        assertEquals(0.003266803319942701, actual, 0.00001);
    }

    @Test
    public void calculateDistance_hasNotValid_shouldZero() {
        //given
        List<LatLngAlt> coordinate = List.of(
                new LatLngAltImpl(54.27379608154297f, 25.376237869262695f, 169, 0, true),
                new LatLngAltImpl(54.27385711669922f, 25.37613296508789f, 167, 7, false),
                new LatLngAltImpl(54.27388381958008f, 25.37611198425293f, 167, 0, true)
        );
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        assertEquals(0, actual, 0.3);
    }

    @Test
    public void calculateDistance_hasNotValid2() {
        //given
        List<LatLngAlt> coordinate = List.of(
                new LatLngAltImpl(54.27379608154297f, 25.376237869262695f, 169, 0, true),
                new LatLngAltImpl(54.27385711669922f, 25.37613296508789f, 167, 7, true),
                new LatLngAltImpl(54.27388381958008f, 25.37611198425293f, 167, 0, false)
        );
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        assertEquals(0.009821071125881577, actual, 0.00001);
    }

    @Test
    public void calculateDistance_distanceBetweenMessagesNotValid_shouldReturn0() {
        //when
        double actual = distanceCalculator.calculateDistance(new LatLngAltImpl(54.27379608154297f, 25.376237869262695f, 169, 0, true), new LatLngAltImpl(50.27379608154297f, 20.376237869262695f, 169, 0, true));
        assertEquals(0, actual, 0.3);
    }

    @Test
    public void calculateDistance_shouldReturnMileage() {
        //when
        double actual = distanceCalculator.calculateDistance(
                new LatLngAltImpl(53.0F, 27.0F, 200, 0, true),
                new LatLngAltImpl(53.1F, 27.1F, 200, 20, true)
        );
        assertEquals(12.973709649170702, actual, 0.00001);
    }

    @Test
    public void calculateDistance_CoordinatesChangesButSpeedZero_shouldReturnZero() {
        //when
        double actual = distanceCalculator.calculateDistance(
                new LatLngAltImpl(53.0F, 27.0F, 200, 0, true),
                new LatLngAltImpl(53.1F, 27.1F, 200, 0, true)
        );
        assertEquals(0, actual, 0);
    }



    private List<LatLngAlt> readCoordinatesFromFile(String fileName){
        List<LatLngAlt> coordinate = new ArrayList<>();
        try(Stream<String> stream = Files.lines(Paths.get(fileName))){
            coordinate = stream
                    .map(coordinateString -> {
                        String[] latLngAltArray = coordinateString.split(";");
                        return new LatLngAltImpl(
                                Float.parseFloat(latLngAltArray[0]),
                                Float.parseFloat(latLngAltArray[1]),
                                Integer.parseInt(latLngAltArray[2]),
                                25, true);
                    })
                    .collect(Collectors.toList());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return coordinate;
    }
}

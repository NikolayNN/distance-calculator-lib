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
        final int expectedMileage = 79207;
        List<LatLngAlt> coordinate = readCoordinatesFromFile(TRACK_79206_FILE);
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        //then
        assertEquals(expectedMileage, actual, 0.5);
    }

    @Test
    public void calculateDistance2() {

        //given
        int expected = 366512;
        List<LatLngAlt> coordinate = readCoordinatesFromFile(TRACK_366510_FILE);
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        //then
        assertEquals(expected, actual, 1);
    }

    @Test
    public void calculateDistance3() {

        //given
        double expected = 795529.7;
        List<LatLngAlt> coordinate = readCoordinatesFromFile(TRACK_795523_FILE);
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        //then
        assertEquals(expected, actual, 0.5);
    }

    @Test
    public void calculateDistanceIfHasZerosCoordinates() {

        //given
        int expected = 78774;
        List<LatLngAlt> coordinate = readCoordinatesFromFile(TRACK_79206_FILE_ZEROS);
        //when
        double actual = distanceCalculator.calculateDistance(coordinate);
        //then
        assertEquals(expected, actual, 0.5);
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
                                Integer.parseInt(latLngAltArray[2]));
                    })
                    .collect(Collectors.toList());
        }catch (IOException e) {
            e.printStackTrace();
        }
        return coordinate;
    }
}

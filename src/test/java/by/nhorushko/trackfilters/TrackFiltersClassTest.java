package by.nhorushko.trackfilters;

import by.nhorushko.distancecalculator.LatLngAlt;
import by.nhorushko.distancecalculator.LatLngAltImpl;
import by.nhorushko.trackfilters.TrackFilters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrackFiltersClassTest {
    private TrackFilters trackFilters;

    @Before
    public void setUp() {
        trackFilters = new TrackFilters();
    }


    @Test
    public void RDPFilter_removeFromLine1() {
        //given
        List<LatLngAlt> coordinates = readCoordinatesFromFile("src/test/resources/tracks/andrewTrack1");
        int expected = 2;

        int actual = trackFilters.RDPFilter(coordinates, 0.0001f).size();
        Assert.assertEquals(expected, actual);
    }


    private List<LatLngAlt> readCoordinatesFromFile(String fileName) {
        List<LatLngAlt> coordinate = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            coordinate = stream
                    .map(coordinateString -> {
                        String[] latLngAltArray = coordinateString.split(";");
                        return new LatLngAltImpl(
                                Instant.ofEpochSecond(System.currentTimeMillis() / 1000),
                                Float.parseFloat(latLngAltArray[0]),
                                Float.parseFloat(latLngAltArray[1]),
                                Integer.parseInt(latLngAltArray[2]),
                                25, true);
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinate;
    }
}

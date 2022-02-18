package by.nhorushko.util;

import by.nhorushko.distancecalculator.LatLngAlt;
import by.nhorushko.distancecalculator.LatLngAltImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageFileReader {

    public static List<LatLngAlt> read(String fileName) {
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

package by.nhorushko.util;

import by.nhorushko.distancecalculator.LatLngAlt;
import by.nhorushko.distancecalculator.LatLngAltImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageFileReader {

    private static final DateTimeFormatter PS_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static final ZoneId UTC = ZoneId.of("UTC");

    public static List<LatLngAlt> read(String fileName) {
        List<LatLngAlt> coordinates = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            coordinates = stream
                    .map(coordinateString -> {
                        String[] source = coordinateString.split(",|;");
                        if (source.length == 3) {
                            return new LatLngAltImpl(
                                    Instant.ofEpochSecond(System.currentTimeMillis() / 1000),
                                    Float.parseFloat(source[0]),
                                    Float.parseFloat(source[1]),
                                    Integer.parseInt(source[2]),
                                    25, true);
                        } else {
                            return new LatLngAltImpl(
                                    convertTime(source[0]),
                                    Float.parseFloat(source[1]),
                                    Float.parseFloat(source[2]),
                                    Integer.parseInt(source[3]),
                                    Integer.parseInt(source[4]),
                                    "VALID".equals(source[5])
                            );
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coordinates;
    }

    private static Instant convertTime(String psTimestamp) {
        return LocalDateTime.parse(psTimestamp, PS_TIMESTAMP_FORMAT).atZone(UTC).toInstant();
    }
}

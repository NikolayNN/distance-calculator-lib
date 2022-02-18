package by.nhorushko.trackfilter;

import by.nhorushko.distancecalculator.LatLngAlt;
import by.nhorushko.distancecalculator.LatLngAltImpl;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrackFilterTest {

    private final TrackFilter trackFilter = new TrackFilter();

    @Test
    public void filter_PointOnLine_returnTwoPoint() {
        List<LatLngAlt> given = latLngAlt(
                latLngAlt(0, 53.918213F, 27.565022F),
                latLngAlt(100, 53.919789F, 27.566183F),
                latLngAlt(200, 53.919789F, 27.566184F),
                latLngAlt(300, 53.919789F, 27.566186F),
                latLngAlt(400, 53.919789F, 27.566191F),
                latLngAlt(500, 53.919789F, 27.566195F)
        );

        List<? extends LatLngAlt> actual = trackFilter.filter(given, 0.0001);

        List<? extends LatLngAlt> expected = List.of(given.get(0), given.get(5));
        assertEquals(expected, actual);
    }


    private LatLngAlt latLngAlt(int time, float latitude, float longitude) {
        return new LatLngAltImpl(Instant.ofEpochSecond(time), latitude, longitude, 0, 20, true);
    }

    private List<LatLngAlt> latLngAlt(LatLngAlt... array) {
        return Arrays.asList(array);
    }
}

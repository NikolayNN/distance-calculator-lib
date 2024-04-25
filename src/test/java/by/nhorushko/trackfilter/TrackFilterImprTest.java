package by.nhorushko.trackfilter;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TrackFilterImprTest {

    private final TrackFilter trackFilter = new TrackFilter();

    @Test
    public void filter_PointOnLine_returnTwoPoint() {
        List<LatLng> given = latLng(
                latLng(53.918213F, 27.565022F),
                latLng(53.919789F, 27.566183F),
                latLng(53.919789F, 27.566184F),
                latLng(53.919789F, 27.566186F),
                latLng(53.919789F, 27.566191F),
                latLng(53.919789F, 27.566195F)
        );

        List<? extends LatLng> actual = trackFilter.filter(given, 0.0001);

        List<? extends LatLng> expected = List.of(given.get(0), given.get(5));
        assertEquals(expected, actual);
    }


    private LatLng latLng(float latitude, float longitude) {
        return new LatLngImpl(latitude, longitude);
    }

    private List<LatLng> latLng(LatLng... array) {
        return Arrays.asList(array);
    }

}

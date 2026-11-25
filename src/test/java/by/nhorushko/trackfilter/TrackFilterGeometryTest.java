package by.nhorushko.trackfilter;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TrackFilterGeometryTest {
    private final TrackFilterI filter = new TrackFilterImpr();

    private static LatLng point(double latitude, double longitude) {
        return new LatLngImpl((float) latitude, (float) longitude);
    }

    private static List<LatLng> square() {
        List<LatLng> points = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            points.add(point(53.9 + i * 0.001, 27));
        }
        for (int i = 0; i < 100; i++) {
            points.add(point(54, 27 + i * 0.001));
        }
        for (int i = 0; i < 100; i++) {
            points.add(point(54 - i * 0.001, 27.1));
        }
        for (int i = 0; i < 100; i++) {
            points.add(point(53.9, 27.1 - i * 0.001));
        }
        return points;
    }

    private static void assertRetained(List<LatLng> input, List<? extends LatLng> result, int... indices) {
        assertEquals(indices.length, result.size());
        for (int i = 0; i < indices.length; i++) {
            assertSame(input.get(indices[i]), result.get(i));
        }
    }

    @Test
    void closedSquare() {
        List<LatLng> points = square();
        points.add(point(53.9, 27));
        for (double epsilon : new double[]{0.00015, 0.00005}) {
            assertRetained(points, filter.filter(points, epsilon), 0, 100, 200, 300, 400);
        }
    }

    @Test
    void openSquare() {
        List<LatLng> points = square();
        assertRetained(points, filter.filter(points, 0.00015), 0, 100, 200, 300, 399);
    }

    @Test
    void outAndBack() {
        List<LatLng> points = new ArrayList<>(square().subList(0, 100));
        for (int i = 98; i >= 40; i--) {
            points.add(points.get(i));
        }
        for (double epsilon : new double[]{0.00015, 0.00005}) {
            assertRetained(points, filter.filter(points, epsilon), 0, 99, 158);
        }
    }

    @Test
    void closedLoopWithinOpenTrack() {
        List<LatLng> points = List.of(point(3, 0), point(0, 0), point(1, 0), point(1, 1),
                point(0, 1), point(0, 0), point(4, 0));
        assertRetained(points, filter.filter(points, 0.00015), 0, 1, 2, 3, 4, 5, 6);
    }

    @Test
    void reversalWithinOpenTrackAtThreshold() {
        List<LatLng> points = List.of(point(0, 0), point(0, 10), point(11, 10), point(10, 10));
        assertRetained(points, filter.filter(points, 1), 0, 1, 2, 3);
    }

    @Test
    void straightLine() {
        List<LatLng> points = square().subList(0, 100);
        assertRetained(points, filter.filter(points, 0.00005), 0, 99);
    }

    @Test
    void identicalPoints() {
        List<LatLng> points = List.of(point(1, 2), point(1, 2), point(1, 2));
        assertRetained(points, filter.filter(points, 0.00015), 0, 2);
    }

    @Test
    void shortInputsReturnedByIdentity() {
        List<LatLng> points = List.of(point(0, 1), point(2, 3));
        for (int size = 0; size <= 2; size++) {
            List<LatLng> input = points.subList(0, size);
            assertSame(input, filter.filter(input, 0));
        }
    }

    @Test
    void zeroEpsilonTerminatesOnCollinearPoints() {
        List<LatLng> points = List.of(point(1, 0), point(1, 1), point(1, 2));
        assertTimeoutPreemptively(Duration.ofSeconds(2),
                () -> assertRetained(points, filter.filter(points, 0), 0, 2));
    }

    @Test
    void deprecatedFilterKeepsTurnaround() {
        List<LatLng> points = List.of(point(0, 0), point(1, 0), point(0, 0));
        assertRetained(points, new TrackFilter().filter(points, 0.00015), 0, 1, 2);
    }

    @Test
    void largeRandomWalk() {
        assertTimeoutPreemptively(Duration.ofMinutes(2), () -> {
            Random random = new Random(77);
            int count = 500_000;
            List<LatLng> points = new ArrayList<>(count);
            double latitude = 53.9;
            double longitude = 27;
            for (int i = 0; i < count; i++) {
                latitude += (random.nextDouble() - 0.5) * 0.001;
                longitude += (random.nextDouble() - 0.5) * 0.001;
                points.add(point(latitude, longitude));
            }
            long start = System.nanoTime();
            List<? extends LatLng> result = filter.filter(points, 0.00015);
            System.out.printf("500,000-point filter: %.1f ms%n", (System.nanoTime() - start) / 1e6);
            assertTrue(result.size() > 2);
            assertSame(points.get(0), result.get(0));
            assertSame(points.get(count - 1), result.get(result.size() - 1));
        });
    }
}

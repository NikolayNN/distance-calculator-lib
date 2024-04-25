package by.nhorushko.classifieddistance;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DistanceClassifierSpeedBasedTest {
    private DistanceClassifierSpeedBased classifier;

    @BeforeEach
    public void setUp() {
        classifier = new DistanceClassifierSpeedBased();
    }

    @Test
    public void testClassifyWithNoMessages() {
        ClassifiedDistanceStorage result = classifier.classify(List.of(), 50);
        assertEquals(0, result.getGpsDistance().getUrban());
        assertEquals(0, result.getGpsDistance().getCountry());
        assertEquals(0, result.getOdoDistance().getUrban());
        assertEquals(0, result.getOdoDistance().getCountry());
    }

    @Test
    public void testClassifyAllUrbanSpeeds() {
        SpeedDistanceable message1 = mockSpeedDistanceable(10, 10, 40); // Speed below 50
        SpeedDistanceable message2 = mockSpeedDistanceable(20, 20, 45); // Speed below 50
        List<SpeedDistanceable> messages = List.of(message1, message2);

        ClassifiedDistanceStorage actual = classifier.classify(messages, 50);

        assertEquals(30, actual.getGpsDistance().getUrban());
        assertEquals(0, actual.getGpsDistance().getCountry());
        assertEquals(30, actual.getOdoDistance().getUrban());
        assertEquals(0, actual.getOdoDistance().getCountry());
    }

    @Test
    public void testClassifyAllCountrySpeeds() {
        SpeedDistanceable message1 = mockSpeedDistanceable(15, 15, 55); // Speed above 50
        SpeedDistanceable message2 = mockSpeedDistanceable(25, 25, 60); // Speed above 50
        List<SpeedDistanceable> messages = List.of(message1, message2);

        ClassifiedDistanceStorage actual = classifier.classify(messages, 50);

        assertEquals(0, actual.getGpsDistance().getUrban());
        assertEquals(40, actual.getGpsDistance().getCountry());
        assertEquals(0, actual.getOdoDistance().getUrban());
        assertEquals(40, actual.getOdoDistance().getCountry());
    }

    @Test
    public void testClassifyMixedSpeeds() {
        SpeedDistanceable message1 = mockSpeedDistanceable(12, 12, 45); // Urban
        SpeedDistanceable message2 = mockSpeedDistanceable(18, 18, 55); // Country
        List<SpeedDistanceable> messages = List.of(message1, message2);

        ClassifiedDistanceStorage actual = classifier.classify(messages, 50);

        assertEquals(12, actual.getGpsDistance().getUrban());
        assertEquals(18, actual.getGpsDistance().getCountry());
        assertEquals(12, actual.getOdoDistance().getUrban());
        assertEquals(18, actual.getOdoDistance().getCountry());
    }

    private SpeedDistanceable mockSpeedDistanceable(double gpsDistance, double odoDistance, int speed) {
        var message = mock(SpeedDistanceable.class);

        when(message.getSpeed()).thenReturn(speed);
        when(message.getOdoDistance()).thenReturn(new Distance(odoDistance, 0));
        when(message.getGpsDistance()).thenReturn(new Distance(gpsDistance, 0));

        return message;
    }
}

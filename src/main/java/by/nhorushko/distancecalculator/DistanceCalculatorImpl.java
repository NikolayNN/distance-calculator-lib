package by.nhorushko.distancecalculator;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.PI;

@Service
public class DistanceCalculatorImpl implements DistanceCalculator {

    public final static double AVERAGE_RADIUS_OF_EARTH_METERS = 6371000;

    public double calculateDistance(List<? extends LatLngAlt> coordinates, DistanceCalculatorSettings settings) {
        if (coordinates == null || coordinates.size() < 2) {
            return 0;
        }
        double result = 0;
        for (int i = 1; i < coordinates.size(); i++) {
            result += calculateDistance(coordinates.get(i - 1), coordinates.get(i), settings);
        }
        return result;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point alt1 Start altitude in meters
     * alt2 End altitude in meters
     *
     * @return Distance in Kilometers if calculated distance > {@param maxValidDistanceMeters} return 0
     */
    public double calculateDistance(LatLngAlt pointA, LatLngAlt pointB, DistanceCalculatorSettings settings) {
        if (isPointsNotCalculable(pointA, pointB, settings)) {
            return 0;
        }
        double latDistance = degToRad(pointA.getLatitude() - pointB.getLatitude());
        double lngDistance = degToRad(pointA.getLongitude() - pointB.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(pointA.getLatitude())) * Math.cos(Math.toRadians(pointB.getLatitude()))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = AVERAGE_RADIUS_OF_EARTH_METERS * c;
        double height = pointA.getAltitude() - pointB.getAltitude();
        distance = Math.sqrt(distance * distance + height * height);
        return checkByMaxDistance(distance, settings);
    }

    private double checkByMaxDistance(double distanceMeters, DistanceCalculatorSettings settings) {
        return distanceMeters > settings.getMaxMessageDistance() ? 0 : distanceMeters / 1000D;
    }

    private boolean isPointsNotCalculable(LatLngAlt pointA, LatLngAlt pointB, DistanceCalculatorSettings settings) {
        return isPointsNotValid(pointA, pointB)
                || hasWrongOrder(pointA, pointB)
                || notHasDetectionSpeed(pointA, pointB, settings)
                || isTimeouted(pointA, pointB, settings);
    }

    private boolean hasWrongOrder(LatLngAlt pointA, LatLngAlt pointB) {
        return pointB.getDatetime().isBefore(pointA.getDatetime());
    }

    private boolean notHasDetectionSpeed(LatLngAlt pointA, LatLngAlt pointB, DistanceCalculatorSettings settings) {
        return pointA.getSpeed() <= settings.getMinDetectionSpeed()
                && pointB.getSpeed() <= settings.getMinDetectionSpeed();
    }

    private boolean isTimeouted(LatLngAlt pointA, LatLngAlt pointB, DistanceCalculatorSettings settings) {
        return pointB.getDatetime().getEpochSecond() - pointA.getDatetime().getEpochSecond() > settings.getMaxMessageTimeout();
    }

    private boolean isPointsNotValid(LatLngAlt pointA, LatLngAlt pointB) {
        return !pointA.isValid()
                || !pointB.isValid()
                || isZeroCoordinates(pointA, pointB);
    }

    private boolean isZeroCoordinates(LatLngAlt pointA, LatLngAlt pointB) {
        return (pointA.getLatitude() == 0 && pointA.getLongitude() == 0) ||
                (pointB.getLatitude() == 0 && pointB.getLongitude() == 0);
    }

    private double degToRad(double deg) {
        return deg / 180.0 * PI;
    }
}

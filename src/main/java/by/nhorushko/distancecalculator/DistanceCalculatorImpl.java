package by.nhorushko.distancecalculator;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.PI;

@Service
public class DistanceCalculatorImpl implements DistanceCalculator {

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371000;

    //https://code.i-harness.com/ru/q/6d18
    //https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude-what-am-i-doi
    public double calculateDistance(List<? extends LatLngAlt> coordinates) {
        if (coordinates == null || coordinates.size() < 2) {
            return 0;
        }
        double result = 0;
        for (int i = 1; i < coordinates.size(); i++) {
            result += calculateDistance(coordinates.get(i - 1), coordinates.get(i));
        }
        return result;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @return Distance in Meters
     */
    public double calculateDistance(LatLngAlt pointA, LatLngAlt pointB) {
        if (!pointA.isValid() || !pointB.isValid() ||
                (pointA.getLatitude() == 0 && pointA.getLongitude() == 0) ||
                (pointB.getLatitude() == 0 && pointB.getLongitude() == 0)) {
            return 0;
        }
        if (pointA.getSpeed() == 0 && pointB.getSpeed() == 0) {
            return 0;
        }
        double latDistance = degToRad(pointA.getLatitude() - pointB.getLatitude());
        double lngDistance = degToRad(pointA.getLongitude() - pointB.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(pointA.getLatitude())) * Math.cos(Math.toRadians(pointB.getLatitude()))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = AVERAGE_RADIUS_OF_EARTH_KM * c;
        double height = pointA.getAltitude() - pointB.getAltitude();
        distance = distance * distance + height * height;
        return Math.sqrt(distance);
    }

    private double degToRad(double deg) {
        return deg / 180.0 * PI;
    }
}

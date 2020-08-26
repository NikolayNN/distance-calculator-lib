package by.horushko.distancecalculator;

import java.util.List;

public interface DistanceCalculator {

    /**
     * @return meters
     */
    double calculateDistance (List<LatLngAlt> coordinates);

    /**
     * @return meters
     */
    double calculateDistanceBetween(LatLngAlt a, LatLngAlt b);
}

package by.nhorushko.distancecalculator;

import java.util.List;

public interface DistanceCalculator {

    /**
     * @return meters
     */
    double calculateDistance (List<? extends LatLngAlt> coordinates);

    /**
     * @return meters
     */
    double calculateDistance(LatLngAlt a, LatLngAlt b);
}

package by.nhorushko.distancecalculator;

import java.util.List;

public interface DistanceCalculator {

    /**
     * @return kilometers
     */
    double calculateDistance(List<? extends LatLngAlt> coordinates, DistanceCalculatorSettings settings);

    /**
     * @return kilometers
     */
    double calculateDistance(LatLngAlt a, LatLngAlt b, DistanceCalculatorSettings distanceCalculatorSettings);
}

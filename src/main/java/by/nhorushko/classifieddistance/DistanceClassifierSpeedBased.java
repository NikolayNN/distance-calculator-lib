package by.nhorushko.classifieddistance;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for classifying distances based on speed thresholds.
 * This class categorizes distances as either urban or country based on whether the speed exceeds a specified threshold.
 */
@Service
public class DistanceClassifierSpeedBased {

    /**
     * Classifies distances based on a given speed threshold to determine if they fall into urban or country categories.
     * This method processes a list of messages that contain speed and distance data, and classifies them into urban or country
     * categories based on whether the speed in each message exceeds a specified threshold.
     *
     * @param messages            The list of messages containing distance and speed data.
     * @param thresholdUrbanSpeed The speed threshold in km/h. Speeds below this threshold are classified as urban, while speeds at or above are classified as country.
     * @return An object of ClassifiedDistanceStorage that contains the total distances classified as urban and country for both GPS and odometer measurements.
     */
    public ClassifiedDistanceStorage classify(List<? extends SpeedDistanceable> messages, int thresholdUrbanSpeed) {
        double totalGpsUrban = 0;
        double totalGpsCountry = 0;
        double totalOdoUrban = 0;
        double totalOdoCountry = 0;
        for (SpeedDistanceable current : messages) {
            if (isCountry(current, thresholdUrbanSpeed)) {
                totalGpsCountry += current.getGpsDistance().getRelative();
                totalOdoCountry += current.getOdoDistance().getRelative();
            } else {
                totalGpsUrban += current.getGpsDistance().getRelative();
                totalOdoUrban += current.getOdoDistance().getRelative();
            }
        }
        ClassifiedDistance gpsDistanceInfo = new ClassifiedDistance(totalGpsUrban, totalGpsCountry);
        ClassifiedDistance odoDistanceInfo = new ClassifiedDistance(totalOdoUrban, totalOdoCountry);
        return new ClassifiedDistanceStorage(gpsDistanceInfo, odoDistanceInfo);
    }

    private boolean isCountry(Speedable message, int thresholdUrbanSpeed) {
        return message.getSpeed() >= thresholdUrbanSpeed;
    }
}

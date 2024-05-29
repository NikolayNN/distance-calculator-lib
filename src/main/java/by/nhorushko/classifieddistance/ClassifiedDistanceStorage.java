package by.nhorushko.classifieddistance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Holds classified distance data for both GPS and odometer measurements.
 * This class is designed to store and manage distance data that has been classified into GPS-based and odometer-based categories.
 * It provides functionality to aggregate this data with similar structures.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassifiedDistanceStorage {
    /**
     * Classified distance based on GPS measurements.
     */
    ClassifiedDistance gpsDistance;

    /**
     * Classified distance based on odometer readings.
     */
    ClassifiedDistance odoDistance;

    @JsonCreator
    public ClassifiedDistanceStorage(@JsonProperty("gpsDistance") ClassifiedDistance gpsDistance,
                                     @JsonProperty("odoDistance") ClassifiedDistance odoDistance) {
        this.gpsDistance = gpsDistance;
        this.odoDistance = odoDistance;
    }

    /**
     * Aggregates this classified distance storage with another instance, combining the respective GPS and odometer distances.
     * This method creates a new instance of {@code ClassifiedDistanceStorage} where each distance type is the sum of the corresponding
     * distances from this instance and the other instance.
     */
    public ClassifiedDistanceStorage plus(ClassifiedDistanceStorage other) {
        return new ClassifiedDistanceStorage(
                gpsDistance.plus(other.gpsDistance),
                odoDistance.plus(other.odoDistance)
        );
    }
}

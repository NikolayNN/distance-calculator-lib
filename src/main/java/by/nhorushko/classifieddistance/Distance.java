package by.nhorushko.classifieddistance;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Represents distance information, providing both an instantaneous and cumulative measurement.
 * This class is useful for tracking distances in scenarios where both the distance from a previous point
 * and the total distance from a starting point are needed.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Distance {

    /**
     * A constant holding a zero distance instance, where both instant and absolute distances are zero.
     */
    public static final Distance ZERO = new Distance(0, 0);

    /**
     * The distance measured from the previous point to the current point.
     * This field can be used to understand the distance covered in a single step or interval.
     */
    double relative;

    /**
     * The cumulative distance measured from the starting point to the current point.
     * This field helps in tracking the total distance covered over a journey or period.
     */
    double absolute;
}

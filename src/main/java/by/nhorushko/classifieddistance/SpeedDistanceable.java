package by.nhorushko.classifieddistance;

/**
 * Extends the Speedable interface to include methods for retrieving distance measurements.
 * This interface is designed for objects that have both speed and distance data,
 * making it suitable for applications such as vehicle tracking systems where GPS and odometer readings are relevant.
 */
public interface SpeedDistanceable extends Speedable {

    /**
     * Retrieves the GPS-based distance for this object.
     * GPS distance can be used to determine the geographic movement of an object over the earth's surface
     *
     * @return the GPS distance of the object as a Distance instance, encapsulating both instant and absolute distances.
     */
    Distance getGpsDistance();

    /**
     * Retrieves the odometer-based distance for this object.
     * Odometer distance measures the cumulative distance traveled by a vehicle and is typically used
     * for more precise distance measurements in controlled environments or where GPS signals are unreliable.
     *
     * @return the odometer distance of the object as a Distance instance, encapsulating both instant and absolute distances.
     */
    Distance getOdoDistance();
}

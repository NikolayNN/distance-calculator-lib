package by.nhorushko.classifieddistance;

/**
 * Provides an interface for retrieving speed data from an object, specifically in kilometers per hour (km/h).
 * This interface is designed for objects representing entities where speed is a measurable attribute, such as vehicles.
 */
public interface Speedable {

    /**
     * Retrieves the speed of this object in kilometers per hour (km/h).
     * This method should be implemented to return the speed value as an integer, reflecting the speed in km/h.
     *
     * @return the speed of the object in km/h as an integer.
     */
    int getSpeed();
}

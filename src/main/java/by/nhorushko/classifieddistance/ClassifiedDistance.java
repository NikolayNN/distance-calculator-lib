package by.nhorushko.classifieddistance;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Represents distances classified into urban and country categories, expressed in kilometers.
 * This class is used to manage and perform operations on distance measurements categorized by these classifications.
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassifiedDistance {

    /**
     * The distance measured in urban areas, expressed in kilometers.
     */
    double urban;

    /**
     * The distance measured in country areas, expressed in kilometers.
     */
    double country;

    /**
     * Calculates the total distance covered by combining urban and country distances.
     * This method is useful for obtaining a comprehensive overview of the total distance covered across both categories.
     *
     * @return The sum of urban and country distances, representing the total distance covered, in kilometers.
     */
    public double getTotal() {
        return urban + country;
    }

    /**
     * Adds the urban and country distances from another {@code ClassifiedDistance} object to this one.
     * This method creates a new {@code ClassifiedDistance} instance where each type of distance is the sum of the
     * corresponding distances in this and the other instance, facilitating the aggregation of distance data.
     *
     * @param other The other {@code ClassifiedDistance} instance to add to this one.
     * @return A new {@code ClassifiedDistance} instance with combined urban and country distances.
     */
    public ClassifiedDistance plus(ClassifiedDistance other) {
        return new ClassifiedDistance(urban + other.urban, country + other.country);
    }
}

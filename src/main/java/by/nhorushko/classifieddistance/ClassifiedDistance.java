package by.nhorushko.classifieddistance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    public static final ClassifiedDistance EMPTY = new ClassifiedDistance(UrbanCategories.EMPTY, 0);

    UrbanCategories urbanCategories;

    /**
     * The distance measured in country areas, expressed in kilometers.
     */
    double country;

    @JsonCreator
    public ClassifiedDistance(@JsonProperty("urbanCategories") UrbanCategories urbanCategories,
                              @JsonProperty("country") double country) {
        this.urbanCategories = urbanCategories;
        this.country = country;
    }

    /**
     * Deprecated constructor
     * This constructor is maintained for backward compatibility
     * Use {@link #withUncategorizedUrban(double, double)} instead.
     */
    @JsonCreator
    @Deprecated
    public ClassifiedDistance(@JsonProperty("urban") double urban,
                              @JsonProperty("country") double country) {
        this.urbanCategories = UrbanCategories.uncategorized(urban);
        this.country = country;
    }

    /**
     * Factory method used when the category of the city cannot be determined.
     * The urban distance is assigned to the 0 - 100,000 population category.
     *
     * @param urban   The distance measured in an urban area.
     * @param country The distance measured in a country area, expressed in kilometers.
     * @return A new {@code ClassifiedDistance} instance with the urban distance categorized as 0 - 100,000 population.
     */
    public static ClassifiedDistance withUncategorizedUrban(double urban, double country) {
        return new ClassifiedDistance(UrbanCategories.uncategorized(urban), country);
    }

    /**
     * The total distance measured in urban areas, expressed in kilometers.
     */
    public double getUrban() {
        return urbanCategories.getTotal();
    }

    /**
     * Calculates the total distance covered by combining urban and country distances.
     * This method is useful for obtaining a comprehensive overview of the total distance covered across both categories.
     *
     * @return The sum of urban and country distances, representing the total distance covered, in kilometers.
     */
    public double getTotal() {
        return getUrban() + country;
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
        return new ClassifiedDistance(urbanCategories.plus(other.urbanCategories), country + other.country);
    }

    /**
     * Represents distances in urban areas, categorized by minimum population boundaries.
     */
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class UrbanCategories {

        public static final UrbanCategories EMPTY = new UrbanCategories(0, 0, 0, 0, 0);

        /**
         * Distance measured in urban areas with a population of 0 - 100,000, expressed in kilometers.
         */
        double urban0;

        /**
         * Distance measured in urban areas with a population of 100,000 - 300000, expressed in kilometers.
         */
        double urban100k;

        /**
         * Distance measured in urban areas with a population of 300,000 - 1,000,000, expressed in kilometers.
         */
        double urban300k;

        /**
         * Distance measured in urban areas with a population of 1,000,000 - 3,000,000, expressed in kilometers.
         */
        double urban1M;

        /**
         * Distance measured in urban areas with a population of 3,000,000 and above, expressed in kilometers.
         */
        double urban3M;

        @JsonCreator
        public UrbanCategories(@JsonProperty("urban0") double urban0,
                               @JsonProperty("urban100k") double urban100k,
                               @JsonProperty("urban300k") double urban300k,
                               @JsonProperty("urban1M") double urban1M,
                               @JsonProperty("urban3M") double urban3M) {
            this.urban0 = urban0;
            this.urban100k = urban100k;
            this.urban300k = urban300k;
            this.urban1M = urban1M;
            this.urban3M = urban3M;
        }

        /**
         * Calculates the total urban distance.
         *
         * @return The sum of all urban distances.
         */
        public double getTotal() {
            return urban0 + urban100k + urban300k + urban1M + urban3M;
        }

        /**
         * Adds the distances from another {@code UrbanCategories} instance to this one.
         * This method creates a new {@code UrbanCategories} instance where each type of distance is the sum of the
         * corresponding distances in this and the other instance.
         *
         * @param other The other {@code UrbanCategories} instance to add to this one.
         * @return A new {@code UrbanCategories} instance with combined distances.
         */
        public UrbanCategories plus(UrbanCategories other) {
            return new UrbanCategories(
                    this.urban0 + other.urban0,
                    this.urban100k + other.urban100k,
                    this.urban300k + other.urban300k,
                    this.urban1M + other.urban1M,
                    this.urban3M + other.urban3M
            );
        }

        /**
         * Factory method used when the category of the city cannot be determined.
         * The mileage is considered as mileage in a city with a population of 0 - 100,000.
         *
         * @param urban The distance measured in an urban area, considered to be in the 0 - 100,000 population category.
         * @return A new {@code UrbanCategories} instance with the urban distance assigned to the 0 - 100,000 population category.
         */
        public static UrbanCategories uncategorized(double urban) {
            return new UrbanCategories(urban, 0, 0, 0, 0);
        }
    }
}

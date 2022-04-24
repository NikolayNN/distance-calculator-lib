package by.nhorushko.distancecalculator;

import java.time.Instant;

public interface LatLngAlt {

    Instant getDatetime();

    float getLatitude();

    float getLongitude();

    int getAltitude();

    int getSpeed();

    /**
     * Message has valid coordinates
     */
    boolean isValid();
}

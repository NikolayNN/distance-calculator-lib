package by.nhorushko.distancecalculator;

import by.nhorushko.trackfilter.LatLng;

import java.time.Instant;

public interface LatLngAlt extends LatLng {

    Instant getDatetime();

    int getAltitude();

    int getSpeed();

    /**
     * Message has valid coordinates
     */
    boolean isValid();
}

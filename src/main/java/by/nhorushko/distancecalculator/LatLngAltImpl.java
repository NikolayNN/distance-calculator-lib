package by.nhorushko.distancecalculator;

import java.time.Instant;
import java.util.Objects;

public class LatLngAltImpl implements LatLngAlt {
    private final Instant datetime;
    private final float latitude;
    private final float longitude;
    private final int altitude;
    private final int speed;
    private final boolean valid;

    public LatLngAltImpl(Instant datetime, float latitude, float longitude, int altitude, int speed, boolean valid) {
        this.datetime = datetime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.valid = valid;
    }

    @Override
    public Instant getDatetime() {
        return datetime;
    }

    @Override
    public float getLatitude() {
        return latitude;
    }

    @Override
    public float getLongitude() {
        return longitude;
    }

    @Override
    public int getAltitude() {
        return altitude;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LatLngAltImpl latLngAlt = (LatLngAltImpl) o;
        return Float.compare(latLngAlt.latitude, latitude) == 0 &&
                Float.compare(latLngAlt.longitude, longitude) == 0 &&
                altitude == latLngAlt.altitude && speed == latLngAlt.speed &&
                valid == latLngAlt.valid && datetime.equals(latLngAlt.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datetime, latitude, longitude, altitude, speed, valid);
    }

    @Override
    public String toString() {
        return "LatLngAltImpl{" +
                "datetime=" + datetime +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", speed=" + speed +
                ", valid=" + valid +
                '}';
    }
}

package by.nhorushko.distancecalculator;

public class LatLngAltImpl implements LatLngAlt {
    private final float latitude;
    private final float longitude;
    private final int altitude;

    public LatLngAltImpl(float latitude, float longitude, int altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    @Override
    public String toString() {
        return "LatLngAltImpl{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                '}';
    }
}

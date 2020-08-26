package by.horushko.distancecalculator;

public class LatLngAltImpl implements LatLngAlt {
    private final double latitude;
    private final double longitude;
    private final int altitude;

    public LatLngAltImpl(double latitude, double longitude, int altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
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

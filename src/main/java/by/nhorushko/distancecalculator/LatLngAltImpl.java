package by.nhorushko.distancecalculator;

public class LatLngAltImpl implements LatLngAlt {
    private final float latitude;
    private final float longitude;
    private final int altitude;
    private final int speed;
    private final boolean valid;

    public LatLngAltImpl(float latitude, float longitude, int altitude, int speed, boolean valid) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.valid = valid;
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
    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return "LatLngAltImpl{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                ", speed=" + speed +
                '}';
    }
}

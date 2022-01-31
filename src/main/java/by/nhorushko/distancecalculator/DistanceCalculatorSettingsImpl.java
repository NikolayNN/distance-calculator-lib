package by.nhorushko.distancecalculator;

import java.util.Objects;

public class DistanceCalculatorSettingsImpl implements DistanceCalculatorSettings {

    private final int minDetectionSpeed;
    private final int maxMessageTimeout;

    public DistanceCalculatorSettingsImpl(int minDetectionSpeed,
                                          int maxMessageTimeout) {
        this.minDetectionSpeed = minDetectionSpeed;
        this.maxMessageTimeout = maxMessageTimeout;
    }

    @Override
    public int getMinDetectionSpeed() {
        return minDetectionSpeed;
    }

    @Override
    public int getMaxMessageTimeout() {
        return maxMessageTimeout;
    }

    public static DistanceCalculatorSettings defaultValue() {
        return new DistanceCalculatorSettingsImpl(0, Integer.MAX_VALUE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistanceCalculatorSettingsImpl that = (DistanceCalculatorSettingsImpl) o;
        return minDetectionSpeed == that.minDetectionSpeed && maxMessageTimeout == that.maxMessageTimeout;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minDetectionSpeed, maxMessageTimeout);
    }

    @Override
    public String toString() {
        return "DistanceCalculatorSettingsImpl{" +
                "minDetectionSpeed=" + minDetectionSpeed +
                ", maxMessageTimeout=" + maxMessageTimeout +
                '}';
    }
}

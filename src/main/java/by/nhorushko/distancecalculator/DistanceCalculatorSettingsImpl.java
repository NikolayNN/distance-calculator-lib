package by.nhorushko.distancecalculator;

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
}

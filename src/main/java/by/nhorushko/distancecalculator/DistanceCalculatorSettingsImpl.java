package by.nhorushko.distancecalculator;

public class DistanceCalculatorSettingsImpl implements DistanceCalculatorSettings {

    private final int minDetectionSpeed;
    private final int maxMessageTimeout;
    private final int maxMessageDistance;

    public DistanceCalculatorSettingsImpl(int minDetectionSpeed,
                                          int maxMessageTimeout,
                                          int maxMessageDistance) {
        this.minDetectionSpeed = minDetectionSpeed;
        this.maxMessageTimeout = maxMessageTimeout;
        this.maxMessageDistance = maxMessageDistance;
    }

    @Override
    public int getMinDetectionSpeed() {
        return minDetectionSpeed;
    }

    @Override
    public int getMaxMessageTimeout() {
        return maxMessageTimeout;
    }

    @Override
    public int getMaxMessageDistance() {
        return maxMessageDistance;
    }

    public static DistanceCalculatorSettings defaultValue() {
        return new DistanceCalculatorSettingsImpl(0, Integer.MAX_VALUE, 500_000);
    }
}

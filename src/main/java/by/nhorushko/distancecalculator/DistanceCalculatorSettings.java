package by.nhorushko.distancecalculator;

public interface DistanceCalculatorSettings {

    /**
     * more this speed is moving, less or equals is not moving
     * @return km/h
     */
    int getMinDetectionSpeed();

    /**
     * Если время между точками больше этой величины то расстояние принимается равным 0
     * @return seconds
     */
    int getMaxMessageTimeout();

    /**
     * Если растояние между точками больше этой величины то расстояние принимается равным 0
     * @return meters
     */
    default int getMaxMessageDistance(){
        return 500_000;
    }
}

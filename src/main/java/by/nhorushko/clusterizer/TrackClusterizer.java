package by.nhorushko.clusterizer;

import by.nhorushko.trackfilter.LatLng;

import java.util.ArrayList;
import java.util.List;

public class TrackClusterizer {

    private static final double EARTH_RADIUS = 6371000;

    /**
     * Кластеризует близко расположенные точки и заменяет их одной существующей точкой (первой в кластере).
     * @param inputList Список точек GPS.
     * @param clusterRadius Максимальное расстояние (в метрах) между точками для создания кластера.
     * @return Список кластеризованных точек.
     */
    public List<LatLng> clusterPoints(List<? extends LatLng> inputList, double clusterRadius) {
        List<LatLng> resultList = new ArrayList<>();
        List<LatLng> currentCluster = new ArrayList<>();

        if (inputList.isEmpty()) {
            return resultList;
        }

        LatLng lastPoint = inputList.get(0);
        currentCluster.add(lastPoint);

        for (int i = 1; i < inputList.size(); i++) {
            LatLng point = inputList.get(i);
            if (distanceBetween(lastPoint, point) <= clusterRadius) {
                currentCluster.add(point);
            } else {
                resultList.add(currentCluster.get(0));
                currentCluster.clear();
                currentCluster.add(point);
            }
            lastPoint = point;
        }

        if (!currentCluster.isEmpty()) {
            resultList.add(currentCluster.get(0));
        }

        return resultList;
    }

    /**
     * Вычисляет расстояние между двумя точками с использованием формулы Haversine.
     * @param p1 Первая точка.
     * @param p2 Вторая точка.
     * @return Расстояние в метрах.
     */
    private double distanceBetween(LatLng p1, LatLng p2) {
        double dLat = Math.toRadians(p2.getLatitude() - p1.getLatitude());
        double dLon = Math.toRadians(p2.getLongitude() - p1.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(p1.getLatitude())) * Math.cos(Math.toRadians(p2.getLatitude())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

}

package by.nhorushko;

import by.nhorushko.trackfilter.LatLng;
import by.nhorushko.trackfilter.LatLngImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrackClusterizerTest {

    private final TrackClusterizer clusterizer = new TrackClusterizer();

    /**
     * Тестирование пустого списка точек.
     */
    @Test
    public void testEmptyList() {
        List<LatLng> inputList = new ArrayList<>();
        double clusterRadius = 10.0; // Любое значение, так как список пустой

        List<LatLng> result = clusterizer.clusterPoints(inputList, clusterRadius);

        assertTrue(result.isEmpty(), "Результат должен быть пустым для пустого входного списка.");
    }

    /**
     * Тестирование списка с одной точкой.
     */
    @Test
    public void testSinglePoint() {
        List<LatLng> inputList = new ArrayList<>();
        inputList.add(new LatLngImpl(55.7558F, 37.6176F)); // Москва
        double clusterRadius = 10.0;

        List<LatLng> result = clusterizer.clusterPoints(inputList, clusterRadius);

        assertEquals(1, result.size(), "Результат должен содержать одну точку.");
        assertEquals(inputList.get(0), result.get(0), "Точка в результате должна совпадать с входной точкой.");
    }

    /**
     * Тестирование списка с точками, которые все входят в один кластер.
     */
    @Test
    public void testAllPointsInOneCluster() {
        List<LatLng> inputList = new ArrayList<>();
        inputList.add(new LatLngImpl(55.7558F, 37.6176F)); // Москва
        inputList.add(new LatLngImpl(55.7559F, 37.6177F));
        inputList.add(new LatLngImpl(55.7560F, 37.6178F));
        double clusterRadius = 50.0; // 50 метров

        List<LatLng> result = clusterizer.clusterPoints(inputList, clusterRadius);

        assertEquals(1, result.size(), "Все точки должны быть объединены в один кластер.");
        assertEquals(inputList.get(0), result.get(0), "Точка в результате должна быть первой входной точкой.");
    }

    /**
     * Тестирование списка с точками, которые образуют несколько кластеров.
     */
    @Test
    public void testMultipleClusters() {
        List<LatLng> inputList = new ArrayList<>();
        // Первый кластер
        inputList.add(new LatLngImpl(55.7558F, 37.6176F)); // Москва
        inputList.add(new LatLngImpl(55.7559F, 37.6177F));
        // Второй кластер
        inputList.add(new LatLngImpl(59.9343F, 30.3351F)); // Санкт-Петербург
        inputList.add(new LatLngImpl(59.9344F, 30.3352F));
        double clusterRadius = 1000.0; // 1000 метров

        List<LatLng> result = clusterizer.clusterPoints(inputList, clusterRadius);

        assertEquals(2, result.size(), "Должно быть два кластера.");
        assertEquals(inputList.get(0), result.get(0), "Первая точка результата должна быть первой точкой первого кластера.");
        assertEquals(inputList.get(2), result.get(1), "Вторая точка результата должна быть первой точкой второго кластера.");
    }

    /**
     * Тестирование с точками на больших расстояниях для проверки корректности расчета расстояния.
     */
    @Test
    public void testPointsWithLargeDistances() {
        List<LatLng> inputList = new ArrayList<>();
        inputList.add(new LatLngImpl(55.7558F, 37.6176F)); // Москва
        inputList.add(new LatLngImpl(48.8566F, 2.3522F));  // Париж
        inputList.add(new LatLngImpl(40.7128F, -74.0060F)); // Нью-Йорк
        double clusterRadius = 500000.0; // 500 километров

        List<LatLng> result = clusterizer.clusterPoints(inputList, clusterRadius);

        assertEquals(3, result.size(), "Все точки должны быть в отдельных кластерах.");
        assertEquals(inputList.get(0), result.get(0), "Первая точка результата должна быть Москва.");
        assertEquals(inputList.get(1), result.get(1), "Вторая точка результата должна быть Париж.");
        assertEquals(inputList.get(2), result.get(2), "Третья точка результата должна быть Нью-Йорк.");
    }

    /**
     * Тестирование с большим количеством точек в одном месте.
     */
    @Test
    public void testLargeNumberOfPointsInOnePlace() {
        List<LatLng> inputList = new ArrayList<>();
        double baseLatitude = 55.7558;  // Москва
        double baseLongitude = 37.6176;

        int numberOfPoints = 1000000;  // Большое количество точек
        double maxOffset = 0.0001;  // Максимальное смещение в градусах (~11 метров)

        // Генерируем точки вокруг базовой координаты с небольшим случайным смещением
        for (int i = 0; i < numberOfPoints; i++) {
            double offsetLat = (Math.random() - 0.5) * maxOffset;
            double offsetLon = (Math.random() - 0.5) * maxOffset;
            double latitude = baseLatitude + offsetLat;
            double longitude = baseLongitude + offsetLon;
            inputList.add(new LatLngImpl((float)latitude, (float)longitude));
        }

        double clusterRadius = 20.0;  // 20 метров

        long start = System.currentTimeMillis();
        List<LatLng> result = clusterizer.clusterPoints(inputList, clusterRadius);
        System.out.println(System.currentTimeMillis() - start);

        assertEquals(1, result.size(), "Все точки должны быть объединены в один кластер.");
        assertEquals(inputList.get(0), result.get(0), "Точка в результате должна быть первой входной точкой.");
    }

}

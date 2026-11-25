package by.nhorushko.trackfilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализует улучшенную версию алгоритма Рамера-Дугласа-Пекера для фильтрации точек на кривой.
 * Эта версия оптимизирует вычисления и эффективность памяти за счет:
 * - Предварительного расчета разностей координат и компонентов расстояния, что сокращает количество повторных операций.
 * - Использования специализированного стека {@link IntStack} для более эффективной работы с индексами.
 * - Эффективного управления памятью и операциями, что делает алгоритм подходящим для больших объемов данных.
 */
public class TrackFilterImpr implements TrackFilterI {

    /**
     * @param inputList Список точек для фильтрации.
     * @param epsilon   Пороговое расстояние до отрезка в градусах.
     * @return Отфильтрованный список точек, удовлетворяющих условиям алгоритма.
     */
    public List<? extends LatLng> filter(List<? extends LatLng> inputList, double epsilon) {
        if (inputList.size() <= 2) {
            return inputList;
        }

        IntStack indexStack = new IntStack(inputList.size());
        indexStack.push(0);
        indexStack.push(inputList.size() - 1);

        boolean[] keepPoint = new boolean[inputList.size()];
        keepPoint[0] = true;
        keepPoint[inputList.size() - 1] = true;

        while (!indexStack.isEmpty()) {
            int endIndex = indexStack.pop();
            int startIndex = indexStack.pop();

            LatLng startPoint = inputList.get(startIndex);
            LatLng endPoint = inputList.get(endIndex);

            // расчет общих значений для сегмента
            double xStart = startPoint.getLongitude();
            double yStart = startPoint.getLatitude();
            double dX = endPoint.getLongitude() - xStart;
            double dY = endPoint.getLatitude() - yStart;
            double lengthSquared = dX * dX + dY * dY;

            double dMax = 0.0;
            int index = startIndex;

            for (int i = startIndex + 1; i < endIndex; i++) {
                double d = pointToSegmentDistance(inputList.get(i), xStart, yStart, dX, dY, lengthSquared);
                if (d > dMax) {
                    index = i;
                    dMax = d;
                }
            }

            if (index != startIndex && dMax >= epsilon) {
                keepPoint[index] = true;
                if (index - startIndex > 1) {
                    indexStack.push(startIndex);
                    indexStack.push(index);
                }
                if (endIndex - index > 1) {
                    indexStack.push(index);
                    indexStack.push(endIndex);
                }
            }
        }

        List<LatLng> resultList = new ArrayList<>();
        for (int i = 0; i < keepPoint.length; i++) {
            if (keepPoint[i]) {
                resultList.add(inputList.get(i));
            }
        }

        return resultList;
    }


    /**
     * Возвращает расстояние в градусах до ближайшей точки отрезка.
     * Если концы отрезка совпадают, измеряет расстояние до его начала.
     */
    private double pointToSegmentDistance(LatLng point, double xStart, double yStart,
                                          double dX, double dY, double lengthSquared) {
        double x = point.getLongitude();
        double y = point.getLatitude();
        double t = 0.0;
        if (lengthSquared > 0.0) {
            double projection = ((x - xStart) * dX + (y - yStart) * dY) / lengthSquared;
            t = Math.max(0.0, Math.min(1.0, projection));
        }
        double dx = x - (xStart + t * dX);
        double dy = y - (yStart + t * dY);
        return Math.sqrt(dx * dx + dy * dy);
    }
}

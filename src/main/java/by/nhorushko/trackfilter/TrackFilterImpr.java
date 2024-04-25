package by.nhorushko.trackfilter;

import by.nhorushko.distancecalculator.LatLngAlt;

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
     * @param epsilon   Пороговое значение расстояния для определения значимости точек.
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
            double dY = endPoint.getLatitude() - startPoint.getLatitude();
            double dX = endPoint.getLongitude() - startPoint.getLongitude();

            double lineLength = Math.sqrt(dY * dY + dX * dX);
            double cross = (double) endPoint.getLongitude() * (double) startPoint.getLatitude() - (double) endPoint.getLatitude() * (double) startPoint.getLongitude();

            double dMax = 0.0;
            int index = startIndex;

            for (int i = startIndex + 1; i < endIndex; i++) {
                double d = pointToLineDistance(inputList.get(i), dY, dX, cross, lineLength);
                if (d > dMax) {
                    index = i;
                    dMax = d;
                }
            }

            if (dMax >= epsilon) {
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
     * Вычисляет перпендикулярное расстояние от точки до линии, заданной двумя точками.
     *
     * @param point      Точка, для которой вычисляется расстояние.
     * @param dY         Разность широт между двумя точками линии.
     * @param dX         Разность долгот между двумя точками линии.
     * @param cross      Предварительно вычисленное перекрестное произведение компонентов линии.
     * @param lineLength Длина линии.
     * @return Перпендикулярное расстояние от точки до линии.
     */
    private double pointToLineDistance(LatLng point, double dY, double dX, double cross, double lineLength) {
        double y0 = point.getLatitude();
        double x0 = point.getLongitude();
        double nominator = Math.abs(dY * x0 - dX * y0 + cross);
        return nominator / lineLength;
    }
}

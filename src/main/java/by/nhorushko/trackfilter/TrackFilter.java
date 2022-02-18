package by.nhorushko.trackfilter;

import by.nhorushko.distancecalculator.LatLngAlt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrackFilter {

    /**
     * Romer-Douglas-Peucker filtration function
     *
     * @param inputList
     * @param epsilon
     * @return
     */
    public List<? extends LatLngAlt> filter(List<? extends LatLngAlt> inputList, double epsilon) {

        if (inputList.size() < 2) {
            return inputList;
        }
        List<Integer> stack = new ArrayList<>();
        List<Boolean> keepPoint = new ArrayList<>();

        stack.add(0, inputList.size() - 1);
        stack.add(0, 0);
        for (int i = 0; i < inputList.size(); i++) {
            keepPoint.add(true);
        }
        while (!stack.isEmpty()) {
            Integer startIndex = stack.get(0);
            Integer endIndex = stack.get(1);
            stack.remove(0);
            stack.remove(0);

            double dMax = 0f;
            Integer index = startIndex;
            for (int i = startIndex + 1; i <= endIndex - 1; i++) {
                if (keepPoint.get(i)) {
                    double d = pointToLineDistance(inputList.get(i), inputList.get(startIndex), inputList.get(endIndex));
                    if (d > dMax) {
                        index = i;
                        dMax = d;
                    }
                }
            }
            if (dMax >= epsilon) {
                stack.add(0, index);
                stack.add(0, startIndex);
                stack.add(0, endIndex);
                stack.add(0, index);
            } else {
                for (int j = startIndex + 1; j <= endIndex - 1; j++) {
                    keepPoint.set(j, false);
                }
            }
        }
        List<LatLngAlt> resultList = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i++) {
            if (keepPoint.get(i)) {
                resultList.add(inputList.get(i));
            }
        }
        return resultList;
    }

    /**
     * function to calculate distance from point to line, line is defined by two points
     *
     * @param point
     * @param linePoint1
     * @param linePoint2
     * @return
     */
    private double pointToLineDistance(LatLngAlt point, LatLngAlt linePoint1, LatLngAlt linePoint2) {
        double y1 = linePoint1.getLatitude();
        double y2 = linePoint2.getLatitude();
        double y0 = point.getLatitude();
        double x1 = linePoint1.getLongitude();
        double x2 = linePoint2.getLongitude();
        double x0 = point.getLongitude();

        double nominator = Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1);
        double denominator = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
        double distance = nominator / denominator;

        return distance;
    }
}

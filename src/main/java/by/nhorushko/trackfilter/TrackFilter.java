package by.nhorushko.trackfilter;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackFilter implements TrackFilterI {

    /**
     * Ramer-Douglas-Peucker filtration function
     *
     * @param inputList
     * @param epsilon   0.0001 - 0.0002 tested optimal values
     * @return
     * @deprecated {@link TrackFilterImpr}
     */
    @Deprecated
    public List<? extends LatLng> filter(List<? extends LatLng> inputList, double epsilon) {
        return new TrackFilterImpr().filter(inputList, epsilon);
    }
}

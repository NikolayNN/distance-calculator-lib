package by.nhorushko.trackfilter;

import by.nhorushko.distancecalculator.LatLngAlt;

import java.util.List;

public interface TrackFilterI {
    List<? extends LatLng> filter(List<? extends LatLng> inputList, double epsilon);
}

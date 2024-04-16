package by.nhorushko.trackfilter;

import by.nhorushko.distancecalculator.LatLngAlt;

import java.util.List;

public interface TrackFilterI {
    List<? extends LatLngAlt> filter(List<? extends LatLngAlt> inputList, double epsilon);
}

package com.screesh.solver;

import java.util.Observable;
import java.util.Observer;

class SolutionAnalyzer implements Observer {
    private int minimumGap;
    private int maximumGap;
    private int currentDayCount = 0;
    private PlacedOverTime lastInSolution;

    @Override
    public void update(Observable o, Object arg) {

    }
}

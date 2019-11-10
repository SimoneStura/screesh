package com.screesh.solver;

import java.util.Observable;
import java.util.Observer;

class SolutionAnalyzer implements Observer {
    private int minimumGap;
    private int maximumGap;
    private int currentDayCount;
    private PlacedOverTime lastInSolution;
    private ConflictsGraph<?> conflicts;
    
    public SolutionAnalyzer(ConflictsGraph<?> conflicts) {
        this.conflicts = conflicts;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

package com.screesh.solver;

import java.util.Observable;
import java.util.Observer;

class SolutionAnalyzer<T extends PlacedOverTime<T>> implements Observer {
    private int minimumGap;
    private int maximumGap;
    private int currentDayCount;
    private T lastInSolution;
    private ConflictsGraph<T> conflicts;
    
    public SolutionAnalyzer(ConflictsGraph<T> conflicts) {
        this.conflicts = conflicts;
    }

    @Override
    public void update(Observable o, Object arg) {
    
    }
}

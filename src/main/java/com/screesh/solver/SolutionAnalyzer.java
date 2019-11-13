package com.screesh.solver;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

class SolutionAnalyzer<T extends PlacedOverTime<T>> implements Observer {
    private int minimumGap;
    private int maximumGap;
    private int currentDayCount;
    private int totalItemsInSolution;
    private ConflictsGraph<T> conflicts;
    
    public SolutionAnalyzer(ConflictsGraph<T> conflicts) {
        this.conflicts = conflicts;
        minimumGap = 0;
        maximumGap = 0;
        currentDayCount = 0;
        totalItemsInSolution = 0;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof BnBSolution) {
            if(arg instanceof ConflictualItem) {
                ConflictualItem<?> involvedItem = (ConflictualItem<?>) arg;
                if(involvedItem.isChosen()) {
                    totalItemsInSolution++;
                } else if(involvedItem.isObscured()) {
                    totalItemsInSolution--;
                }
            }
        }
    }
    
    //TODO: cambiare la gestione in modo da non interpellare il ConflictsGraph
    public Set<T> getObscuredItems() {
        return conflicts.getObscuredItems();
    }
    
    public int getMinimumGap() {
        return minimumGap;
    }
    
    public int getMaximumGap() {
        return maximumGap;
    }
    
    public int getCurrentDayCount() {
        return currentDayCount;
    }
    
    public int getTotalItemsInSolution() {
        return totalItemsInSolution;
    }
}

package com.screesh.solver;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

class SolutionAnalyzer<T extends PlacedOverTime<T>> implements Observer {
    private int minimumGap;
    private int maximumGap;
    private int currentDayCount;
    private int totalItemsInSolution;
    private int bestSolution;
    private ConflictsGraph<T> conflicts;
    
    SolutionAnalyzer(ConflictsGraph<T> conflicts) {
        this.conflicts = conflicts;
        reset();
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
    Set<T> getObscuredItems() {
        return conflicts.getObscuredItems();
    }
    
    int getMinimumGap() {
        return minimumGap;
    }
    
    int getMaximumGap() {
        return maximumGap;
    }
    
    int getCurrentDayCount() {
        return currentDayCount;
    }
    
    int getTotalItemsInSolution() {
        return totalItemsInSolution;
    }
    
    int getBestSolution() {
        return bestSolution;
    }
    
    void setBestSolution(int bestSolution) {
        this.bestSolution = bestSolution;
    }
    
    void reset() {
        minimumGap = 0;
        maximumGap = 0;
        currentDayCount = 0;
        totalItemsInSolution = 0;
        bestSolution = 0;
    }
}

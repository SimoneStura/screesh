package com.screesh.solver;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Observable;
import java.util.SortedSet;
import java.util.TreeSet;

class BnBSolution<T extends PlacedOverTime<T>> extends Observable {
    private SortedSet<ConflictualItem<T>> solution;
    private ConflictsGraph<T> conflictsGraph;

    public BnBSolution(ConflictsGraph<T> conflicts) {
        solution = new TreeSet<>();
        this.conflictsGraph = conflicts;
    }

    public void add(ConflictualItem<T> elem) {
        conflictsGraph.choose(elem);
    
        Assert.assertFalse(elem.isObscured());
        Assert.assertTrue(elem.isChosen());
        solution.add(elem);
    
        setChanged();
        notifyObservers(elem);
    }

    public void removeLast() {
        conflictsGraph.rollbackOfChoosing();
        
        ConflictualItem<T> last = solution.last();
        solution.remove(last);

        setChanged();
        if(solution.isEmpty())
            last = null;
        else
            last = solution.last();
        notifyObservers(last);
    }
    
    public SortedSet<T> getSolution() {
        TreeSet<T> solutionItems = new TreeSet<>();
        solution.forEach(v -> solutionItems.add(v.getItem()));
        return solutionItems;
    }

    public void reset() {
        solution = new TreeSet<>();
    }
}

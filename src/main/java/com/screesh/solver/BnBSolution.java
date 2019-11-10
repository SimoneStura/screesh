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
        Assert.assertTrue(last.isObscured());
        Assert.assertFalse(last.isChosen());
        solution.remove(last);

        setChanged();
        last = solution.last();
        notifyObservers(last);
    }
    
    public SortedSet<T> getSolution() {
        TreeSet<T> solutionItems = new TreeSet<>();
        solution.forEach(v -> solutionItems.add(v.getItem()));
        return solutionItems;
    }
}

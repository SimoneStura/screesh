package com.screesh.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

public class ConflictsSolver<E extends PlacedOverTime<E>> {
    private BnBSolution<E> branchNbound;
    private ConflictsGraph<E> conflicts;
    private SolutionAnalyzer analyzer;
    private SeekerForTheBest seeker;
    
    public ConflictsSolver() {
        conflicts = new ConflictsGraph<>();
        branchNbound = new BnBSolution<>(conflicts);
        analyzer = new SolutionAnalyzer(conflicts);
        seeker = new SeekerForTheBest(analyzer);
    
        branchNbound.addObserver(analyzer);
    }
    
    public int getNumItems() {
        return 0;
    }
    
    public int getNumGroups() {
        return 0;
    }
    
    public void addElement(E item) {}
    
    public void addGroup(Collection<E> items) {}
    
    public void setConflict(E item1, E item2) {}
    
    public void mustIncludeOne(Collection<E> items) {}
    
    public boolean mustInclude(E toBeIncluded) {
        return false;
    }
    
    public int bestResult() {
        return 0;
    }
    
    public List<SortedSet<E>> allGoodSolutions() {
        return null;
    }
    
    public SortedSet<E> bestSolution() {
        return null;
    }
}

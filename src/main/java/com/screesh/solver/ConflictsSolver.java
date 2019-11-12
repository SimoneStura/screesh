package com.screesh.solver;

import org.junit.Assert;

import java.util.*;
import java.util.stream.Collectors;

public class ConflictsSolver<T extends PlacedOverTime<T>> {
    private HashMap<T, ConflictualItem<T>> solutionItems;
    private HashMap<T, HashSet<ConflictualItem<T>>> groupMembership;
    private BnBSolution<T> branchAndBound;
    private ConflictsGraph<T> conflicts;
    private SolutionAnalyzer<T> analyzer;
    private SeekerForTheBest<T> seeker;
    private int numItems;
    private int numGroups;
    
    public ConflictsSolver() {
        solutionItems = new HashMap<>();
        groupMembership = new HashMap<>();
        numItems = 0;
        numGroups = 0;
        
        conflicts = new ConflictsGraph<>();
        branchAndBound = new BnBSolution<>(conflicts);
        analyzer = new SolutionAnalyzer<>(conflicts);
        seeker = new SeekerForTheBest<>(analyzer);
    
        branchAndBound.addObserver(analyzer);
    }
    
    public int getNumItems() {
        return numItems;
    }
    
    public int getNumGroups() {
        return numGroups;
    }
    
    public void addItem(T itemToAdd) {
        addIfAbsent(itemToAdd);
    }
    
    public void addGroup(Collection<T> itemsToAdd) {
        HashSet<ConflictualItem<T>> conflictualGroup = new HashSet<>(itemsToAdd.size());
        for(T item : itemsToAdd) {
            Assert.assertFalse(groupMembership.containsKey(item));
            ConflictualItem<T> currentConflictual = addIfAbsent(item);
            
            for(ConflictualItem<T> brother : conflictualGroup) {
                setConflict(currentConflictual, brother);
            }
            conflictualGroup.add(currentConflictual);
            groupMembership.put(item, conflictualGroup);
        }
        
        if(itemsToAdd.size() > 0) numGroups++;
    }
    
    private ConflictualItem<T> addIfAbsent(T itemToAdd) {
        ConflictualItem<T> itemWithConflicts = new ConflictualItem<>(itemToAdd);
        ConflictualItem<T> alreadyAdded = solutionItems.putIfAbsent(itemToAdd, itemWithConflicts);
    
        if(alreadyAdded == null) {
            conflicts.addVertex(itemWithConflicts);
            alreadyAdded = itemWithConflicts;
            numItems++;
        }
        
        return alreadyAdded;
    }
    
    public void setConflict(T item1, T item2) {
        ConflictualItem<T> vertex1 = addIfAbsent(item1);
        ConflictualItem<T> vertex2 = addIfAbsent(item2);
        setConflict(vertex1, vertex2);
    }
    
    private void setConflict(ConflictualItem<T> vertex1, ConflictualItem<T> vertex2) {
        conflicts.addEdge(vertex1, vertex2);
    }
    
    public boolean mustIncludeOne(SortedSet<T> items) {
        items.forEach(this::addIfAbsent);
        return seeker.mustIncludeOne(items);
    }
    
    public boolean mustInclude(T toBeIncluded) {
        return seeker.mustInclude(toBeIncluded);
    }
    
    public int bestResult() {
        return 0;
    }
    
    public List<SortedSet<T>> allGoodSolutions() {
        return null;
    }
    
    public SortedSet<T> bestSolution() {
        return null;
    }
}

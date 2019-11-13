package com.screesh.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

class SeekerForTheBest<T extends PlacedOverTime<T>> {
    private SolutionAnalyzer<T> analyzer;
    private SortedSet<T> requiredItems;
    private ArrayList<SortedSet<T>> groupsToInclude;
    
    SeekerForTheBest(SolutionAnalyzer<T> analyzer) {
        this.analyzer = analyzer;
        requiredItems = new TreeSet<>();
        groupsToInclude = new ArrayList<>();
    }
    
    boolean isWorthToContinue(SortedSet<ConflictualItem<T>> remainingItems) {
        return isWorthToContinue(remainingItems, 0);
    }
    
    boolean isWorthToContinue(SortedSet<ConflictualItem<T>> remainingItems, int recordTotMovies) {
        long maximumAddable = remainingItems.stream()
                .filter(x -> !x.isObscured())
                .count();
        return analyzer.getTotalItemsInSolution() + maximumAddable >= recordTotMovies;
    }
    
    boolean isBestSolution() {
        return false;
    }
    
    boolean mustInclude(T item) {
        for (T req : requiredItems) {
            if (item.isInConflictWith(req))
                return false;
        }
        
        for(SortedSet<T> group : groupsToInclude) {
            if(!canFitGroup(item, group))
                return false;
        }
        
        requiredItems.add(item);
        return true;
    }
    
    boolean mustIncludeOne(SortedSet<T> items) {
        for (T req : requiredItems) {
            if (!canFitGroup(req, items)) {
                return false;
            }
        }
        
        for (T current : items) {
            boolean canFitAllGroups = true;
            for (SortedSet<T> group : groupsToInclude) {
                canFitAllGroups = canFitAllGroups && canFitGroup(current, group);
            }
            
            if (canFitAllGroups) {
                return groupsToInclude.add(items);
            }
        }
        
        return false;
    }
    
    //TODO: trasformare in un metodo di istanza
    private boolean canFitGroup(T item, Collection<T> group) {
        if (group.contains(item))
            return true;
        
        for (T groupItem : group) {
            if (!item.isInConflictWith(groupItem)) {
                return true;
            }
        }
        
        return false;
    }
}

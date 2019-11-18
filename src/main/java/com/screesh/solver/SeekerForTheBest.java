package com.screesh.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

class SeekerForTheBest<T extends PlacedOverTime<T>> {
    private SolutionAnalyzer<T> analyzer;
    private SortedSet<ConflictualItem<T>> requiredItems;
    private ArrayList<SortedSet<ConflictualItem<T>>> groupsToInclude;
    
    SeekerForTheBest(SolutionAnalyzer<T> analyzer) {
        this.analyzer = analyzer;
        requiredItems = new TreeSet<>();
        groupsToInclude = new ArrayList<>();
    }
    
    boolean isWorthToContinue(SortedSet<ConflictualItem<T>> remainingItems) {
        for (ConflictualItem<T> req : requiredItems) {
            if (req.isObscured())
                return false;
        }
        
        for (SortedSet<ConflictualItem<T>> group : groupsToInclude) {
            boolean allExcluded = true;
            for (ConflictualItem<T> groupItem : group) {
                allExcluded = groupItem.isObscured();
                if (!allExcluded)
                    break;
            }
            if (allExcluded)
                return false;
        }
        
        long maximumAddable = 0;
        if (remainingItems != null) {
            maximumAddable = remainingItems.stream()
                    .filter(x -> !x.isObscured())
                    .count();
        }
        
        return analyzer.getTotalItemsInSolution() + maximumAddable >= analyzer.getBestSolution();
    }
    
    boolean isBestSolution(SortedSet<T> currentSolution) {
        if(!isWorthToContinue(null))
            return false;
        int currentLength = currentSolution.size();
        if (currentLength > analyzer.getBestSolution())
            analyzer.setBestSolution(currentLength);
        return currentLength == analyzer.getBestSolution();
    }
    
    boolean mustInclude(ConflictualItem<T> conflictual) {
        for (ConflictualItem<T> req : requiredItems) {
            if (conflictual.getItem().isInConflictWith(req.getItem()))
                return false;
        }
        
        for (SortedSet<ConflictualItem<T>> group : groupsToInclude) {
            if (!canFitGroup(conflictual, group))
                return false;
        }
        
        requiredItems.add(conflictual);
        return true;
    }
    
    boolean mustIncludeOne(SortedSet<ConflictualItem<T>> items) {
        for (ConflictualItem<T> req : requiredItems) {
            if (!canFitGroup(req, items)) {
                return false;
            }
        }
        
        for (ConflictualItem<T> current : items) {
            boolean canFitAllGroups = true;
            for (SortedSet<ConflictualItem<T>> group : groupsToInclude) {
                canFitAllGroups = canFitAllGroups && canFitGroup(current, group);
            }
            
            if (canFitAllGroups) {
                return groupsToInclude.add(items);
            }
        }
        
        return false;
    }
    
    //TODO: trasformare in un metodo di istanza
    private boolean canFitGroup(ConflictualItem<T> conflictual, Collection<ConflictualItem<T>> group) {
        if (group.contains(conflictual))
            return true;
        
        for (ConflictualItem<T> groupItem : group) {
            if (!conflictual.getItem().isInConflictWith(groupItem.getItem())) {
                return true;
            }
        }
        
        return false;
    }
}

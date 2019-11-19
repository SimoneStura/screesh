package com.screesh.solver;

import java.util.stream.Collectors;
import org.junit.Assert;

import java.util.*;

public class ConflictsSolver<T extends PlacedOverTime<T>> {
    private HashMap<T, ConflictualItem<T>> solutionItems;
    private HashMap<T, HashSet<ConflictualItem<T>>> groupMembership;
    private TreeSet<ConflictualItem<T>> sortedSolutionItems;
    private BnBSolution<T> branchAndBound;
    private ConflictsGraph<T> conflicts;
    private SolutionAnalyzer<T> analyzer;
    private SeekerForTheBest<T> seeker;
    private int numItems;
    private int numGroups;
    
    public ConflictsSolver() {
        solutionItems = new HashMap<>();
        groupMembership = new HashMap<>();
        sortedSolutionItems = new TreeSet<>();
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
    
    public void remove(T itemToRemove) {
        ConflictualItem<T> toRemove = solutionItems.getOrDefault(itemToRemove, null);
        if(toRemove == null)
            return;
        solutionItems.remove(itemToRemove);
        sortedSolutionItems.remove(toRemove);
        conflicts.removeVertex(toRemove);
        numItems--;
    
        HashSet<ConflictualItem<T>> group = groupMembership.getOrDefault(itemToRemove, null);
        if(group == null)
            return;
        group.remove(toRemove);
        if(group.size() == 0)
            numGroups--;
        groupMembership.remove(itemToRemove);
    }
    
    public void remove(Collection<T> itemsToRemove) {
        for(T itemToRemove : itemsToRemove) {
            remove(itemToRemove);
        }
    }
    
    private ConflictualItem<T> addIfAbsent(T itemToAdd) {
        ConflictualItem<T> itemWithConflicts = new ConflictualItem<>(itemToAdd);
        ConflictualItem<T> alreadyAdded = solutionItems.putIfAbsent(itemToAdd, itemWithConflicts);
    
        if(alreadyAdded == null) {
            sortedSolutionItems.add(itemWithConflicts);
            conflicts.addVertex(itemWithConflicts);
            alreadyAdded = itemWithConflicts;
            numItems++;
            searchForConflicts(alreadyAdded);
        }
        
        return alreadyAdded;
    }

    private void searchForConflicts(ConflictualItem<T> item) {
        for(ConflictualItem<T> conflictual : sortedSolutionItems) {
            if(conflictual.equals(item))
                continue;
            if(conflictual.getItem().isInConflictWith(item.getItem()))
                setConflict(conflictual, item);
        }
    }
    
    private void setConflict(ConflictualItem<T> vertex1, ConflictualItem<T> vertex2) {
        conflicts.addEdge(vertex1, vertex2);
    }
    
    public boolean mustIncludeOne(Collection<T> items) {
        SortedSet<ConflictualItem<T>> conflictualItems = new TreeSet<>();
        for(T item : items)
            conflictualItems.add(addIfAbsent(item));
            
        return seeker.mustIncludeOne(conflictualItems);
    }
    
    public boolean mustInclude(T toBeIncluded) {
        ConflictualItem<T> conflictual = addIfAbsent(toBeIncluded);
        return seeker.mustInclude(conflictual);
    }
    
    //TODO: scegliere se è meglio avere tempi doppi oppure risparmiare sulla memoria
    public int bestResult() {
        return 0;
    }
    
    public List<SortedSet<T>> allGoodSolutions() {
        analyzer.reset();
        SolutionBin bin = new SolutionBin(sortedSolutionItems);
        bin = goodSolutionsFinder(bin);
        return bin.allGoodSolutions;
    }
    
    private SolutionBin goodSolutionsFinder(SolutionBin inputBin) {
        SortedSet<ConflictualItem<T>> addables = findAddables(inputBin.stillToChoose);
        SolutionBin outputBin = new SolutionBin(addables);
        if(addables == null || addables.size() == 0) {
            if(seeker.isBestSolution(branchAndBound.getSolution()))
                inputBin.addGoodSolution(branchAndBound.getSolution());
            return inputBin;
        }
        ConflictualItem<T> firstToChoose = outputBin.stillToChoose.first();
        outputBin.stillToChoose.remove(firstToChoose);
        
        branchAndBound.add(firstToChoose);
        if(seeker.isWorthToContinue(outputBin.stillToChoose))
            goodSolutionsFinder(outputBin);
        
        branchAndBound.removeLast();
        if(seeker.isWorthToContinue(outputBin.stillToChoose))
            goodSolutionsFinder(outputBin);

        for(SortedSet<T> newSolution : outputBin.allGoodSolutions)
            inputBin.addGoodSolution(newSolution);
        return inputBin;
    }
    
    private SortedSet<ConflictualItem<T>> findAddables(SortedSet<ConflictualItem<T>> stillToChoose) {
        return stillToChoose.stream().filter(x -> !x.isObscured()).collect(Collectors.toCollection(TreeSet::new));
    }
    
    private class SolutionBin {
        SortedSet<ConflictualItem<T>> stillToChoose;
        List<SortedSet<T>> allGoodSolutions;
    
        SolutionBin(SortedSet<ConflictualItem<T>> stillToChoose) {
            this.stillToChoose = stillToChoose;
            allGoodSolutions = new ArrayList<>();
        }
        
        void addGoodSolution(SortedSet<T> goodSolution) {
            if(!allGoodSolutions.isEmpty() && allGoodSolutions.get(0).size() < goodSolution.size())
                allGoodSolutions.clear();
    
            if(allGoodSolutions.isEmpty() || allGoodSolutions.get(0).size() == goodSolution.size())
                allGoodSolutions.add(goodSolution);
        }
    }
}

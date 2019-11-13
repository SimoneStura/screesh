package com.screesh.solver;

import org.junit.Assert;

import java.util.*;

class ConflictsGraph<T extends PlacedOverTime<T>> extends Graph<ConflictualItem<T>> {
    private Stack<ConflictGraphAction<T>> choosingActions;
    
    ConflictsGraph() {
        choosingActions = new Stack<>();
    }
    
    public void choose(ConflictualItem<T> node) {
        Assert.assertFalse(node.isObscured());
        
        node.setChosen(true);
        ArrayList<ConflictualItem<T>> obscuredItems = new ArrayList<>();
        for (ConflictualItem<T> conflict : super.edges.get(node)) {
            if(!conflict.isObscured()) {
                conflict.setObscured(true);
                obscuredItems.add(conflict);
            }
        }
        choosingActions.push(new ConflictGraphAction<T>(node, obscuredItems));
    }
    
    public void rollbackOfChoosing() {
        if(choosingActions.isEmpty())
            return;
        ConflictGraphAction<T> lastAction = choosingActions.pop();
        lastAction.getMainItem().setObscured(true);
        for(ConflictualItem<T> previouslyObscured : lastAction.getInvolvedItems()) {
            previouslyObscured.setObscured(false);
        }
    }
    
    public Set<T> getObscuredItems() {
        HashSet<T> obscuredOnes = new HashSet<>();
        for(ConflictGraphAction<T> action : choosingActions) {
            for(ConflictualItem<T> obscuredByAction : action.getInvolvedItems()) {
                obscuredOnes.add(obscuredByAction.getItem());
            }
        }
        return obscuredOnes;
    }
}

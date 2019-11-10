package com.screesh.solver;

import java.util.ArrayList;

class ConflictGraphAction<T extends PlacedOverTime<T>> {
    private ConflictualItem<T> mainItem;
    private ArrayList<ConflictualItem<T>> involvedItems;
    
    public ConflictGraphAction(ConflictualItem<T> mainItem, ArrayList<ConflictualItem<T>> involvedItems) {
        this.mainItem = mainItem;
        this.involvedItems = involvedItems;
    }
    
    public ConflictualItem<T> getMainItem() {
        return mainItem;
    }
    
    public ArrayList<ConflictualItem<T>> getInvolvedItems() {
        return involvedItems;
    }
}

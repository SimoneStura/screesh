package com.screesh.solver;

public class ConflictualItem<T extends PlacedOverTime<T>> implements Comparable<ConflictualItem<T>> {
    private T item;
    private boolean chosen;
    private boolean obscured;
    
    public ConflictualItem(T  item) {
        this.item = item;
    }
    
    public T getItem() {
        return item;
    }
    
    boolean isChosen() {
        return chosen;
    }
    
    void setChosen(boolean chosen) {
        this.chosen = chosen;
        if(chosen)
            obscured = false;
    }
    
    boolean isObscured() {
        return obscured;
    }
    
    void setObscured(boolean obscured) {
        this.obscured = obscured;
        if(obscured)
            chosen = false;
    }

    @Override
    public int compareTo(ConflictualItem<T> o) {
        if(o == null)
            return 1;
        return item.compareTo(o.item);
    }
}

package com.screesh.solver;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConflictualItem)) return false;
        ConflictualItem<?> that = (ConflictualItem<?>) o;
        return item.equals(that.item);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(item);
    }
    
    @Override
    public int compareTo(ConflictualItem<T> o) {
        if(o == null)
            return 1;
        return item.compareTo(o.item);
    }
}

package com.screesh.solver;

public class ConflictualItem<T extends PlacedOverTime<T>> implements Comparable<ConflictualItem<T>> {
    private T item;

    public ConflictualItem(T  item) {
        this.item = item;
    }

    @Override
    public int compareTo(ConflictualItem<T> o) {
        if(o == null)
            return 1;
        return item.compareTo(o.item);
    }
}

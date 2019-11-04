package com.screesh.solver;

import java.util.Observable;
import java.util.SortedSet;
import java.util.TreeSet;

class BnBSolution<E extends PlacedOverTime<E>> extends Observable {
    private SortedSet<E> solution;

    public BnBSolution() {
        solution = new TreeSet<>();

    }

    public void add(E elem) {

        setChanged();
        notifyObservers(elem);
    }

    public void remove(E elem) {

        setChanged();
        PlacedOverTime last = solution.last();
        notifyObservers(last);
    }
}

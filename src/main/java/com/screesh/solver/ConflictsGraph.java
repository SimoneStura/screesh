package com.screesh.solver;

import java.util.SortedSet;

public class ConflictsGraph<T extends PlacedOverTime<T>> {
    private SortedSet<Node<T>> conflictualItems;


    private static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
        T value;
        boolean obscured;
        boolean chosen;

        Node(T value) {
            this.value = value;
        }

        @Override
        public int compareTo(Node<T> o) {
            return value.compareTo(o.value);
        }
    }
}

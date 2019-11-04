package com.screesh.solver;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph<T> {
    private HashMap<T, ArrayList<T>> edges;

    public void addVertex(T node) {
        boolean ctrl = !edges.containsKey(node);

        if(ctrl) {
            edges.put(node, new ArrayList<>());
        }
    }

    public void addEdge(T node1, T node2) {
    }
}

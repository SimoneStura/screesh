package com.screesh.solver;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph<T> {
    protected HashMap<T, ArrayList<T>> edges;

    public void addVertex(T node) {
        boolean alreadyAdded = edges.containsKey(node);

        if(!alreadyAdded)
            edges.put(node, new ArrayList<>());
    }

    public void addEdge(T node1, T node2) {
        addVertex(node1);
        addVertex(node2);
        
        ArrayList<T> edgesNode1 = edges.get(node1);
        edgesNode1.add(node2);
        ArrayList<T> edgesNode2 = edges.get(node2);
        edgesNode2.add(node1);
    }
    
    public ArrayList<T> getEdges(T node) {
        return edges.get(node);
    }
}

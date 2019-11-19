package com.screesh.solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Graph<T> {
    protected HashMap<T, HashSet<T>> edges;
    
    public Graph() {
        edges = new HashMap<>();
    }

    public void addVertex(T node) {
        boolean alreadyAdded = edges.containsKey(node);

        if(!alreadyAdded)
            edges.put(node, new HashSet<>());
    }
    
    public void removeVertex(T node) {
        HashSet<T> edgesToRemove = edges.getOrDefault(node, null);
        if(edgesToRemove == null)
            return;
        
        for(T edge : edgesToRemove)
            edges.get(edge).remove(node);
        
        edges.remove(node);
    }

    public void addEdge(T node1, T node2) {
        addVertex(node1);
        addVertex(node2);

        HashSet<T> edgesNode1 = edges.get(node1);
        edgesNode1.add(node2);
        HashSet<T> edgesNode2 = edges.get(node2);
        edgesNode2.add(node1);
    }
    
    public void removeEdge(T node1, T node2) {
        HashSet<T> edgesOfFirst = edges.getOrDefault(node1, null);
        HashSet<T> edgesOfSecond = edges.getOrDefault(node2, null);
        if(edgesOfFirst != null && edgesOfSecond != null) {
            edgesOfFirst.remove(node2);
            edgesOfSecond.remove(node1);
        }
    }
    
    public Set<T> getEdges(T node) {
        return edges.get(node);
    }
}

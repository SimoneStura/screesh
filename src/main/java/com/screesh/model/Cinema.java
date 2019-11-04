package com.screesh.model;

import java.io.Serializable;
import java.util.*;

public class Cinema implements Comparable<Cinema>, Serializable {
    private String name;
    private Map<Cinema, Integer> distance = new HashMap<>(); //TODO: sostituire questo con un manager centralizzato

    public Cinema(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDistance(Cinema c, int dist) {
        distance.put(c, dist);
    }

    public String getName() {
        return name;
    }

    public int getDistance(Cinema c) {
        if(c == null || c.equals(this)) return 0;
        Integer d = distance.get(c);
        if(d == null) return 0;
        return d;
    }

    public boolean equals(Object obj) {
        if(obj instanceof Cinema)
            return name.equals(((Cinema)obj).name);
        return false;
    }

    public int compareTo(Cinema c) {
        return name.compareToIgnoreCase(c.name);
    }

    public String toString() {
        return name;
    }
}

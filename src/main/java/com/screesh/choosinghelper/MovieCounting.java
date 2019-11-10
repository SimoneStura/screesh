package com.screesh.choosinghelper;

import com.screesh.model.Movie;

public class MovieCounting implements Comparable<MovieCounting> {
    private Movie value;
    private int counting;
    
    public MovieCounting(Movie value, int counting) {
        this.value = value;
        this.counting = counting;
    }
    
    public Movie getValue() {
        return value;
    }
    
    public int getCounting() {
        return counting;
    }
    
    public int compareTo(MovieCounting o) {
        return Integer.compare(this.counting, o.counting);
    }
    
    public String toString() {
        return value.toString() + " [excluded " + counting + " times]";
    }
}
package com.screesh.model;

import java.io.Serializable;
import java.util.Objects;

public class Movie implements Comparable<Movie>, Serializable {
    private String title;
    private int year;
    private int runtime;
    private String directedBy;

    public Movie(String title, int year, int runtime) {
        this.title = title;
        this.year = year;
        this.runtime = runtime;
    }
    
    @Override
    public String toString() {
        return title +
                " (" + year + ")" +
                " " + runtime + "'";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                title.equals(movie.title);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, year);
    }
    
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getDirectedBy() {
        return directedBy;
    }

    public void setDirectedBy(String directedBy) {
        this.directedBy = directedBy;
    }
    
    @Override
    public int compareTo(Movie o) {
        int compared = title.compareToIgnoreCase(o.title);
        if(compared == 0)
            compared = Integer.compare(year, o.year);
        
        return compared;
    }
}

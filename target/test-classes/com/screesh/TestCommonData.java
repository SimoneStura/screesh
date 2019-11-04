package com.screesh;

import com.screesh.model.Movie;

import java.util.ArrayList;

public class TestCommonData {
    
    public static ArrayList<Movie> createMovieList(int totMovies) {
        ArrayList<Movie> movies = new ArrayList<>();
        for(int i = 0; i < totMovies; i++) {
            Movie movieToAdd = createMovie(String.valueOf(i + 1));
            movies.add(movieToAdd);
        }
        
        return movies;
    }
    
    public static Movie createMovie(String appendToTitle) {
        int year = (int) (1900 + Math.random()*100);
        int runtime = (int) (90 + Math.random()*100);
        return new Movie("Title " + appendToTitle, year, runtime);
    }
}
package com.screesh.choosinghelper;

import com.screesh.model.Movie;

public class ChoiceMade {
    private Movie movie;
    private boolean excluded;
    
    ChoiceMade(Movie movie, boolean excluded) {
        this.movie = movie;
        this.excluded = excluded;
    }
    
    public Movie getMovie() {
        return movie;
    }
    
    public boolean isExcluded() {
        return excluded;
    }
}

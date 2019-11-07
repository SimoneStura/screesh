package com.screesh.choosinghelper;

import com.screesh.model.Movie;

public class ChoiceMade {
    private Movie movie;
    private Action chosenAction;
    
    public ChoiceMade(Movie movie) {
        this.movie = movie;
        this.chosenAction = Action.PENDING;
    }
    
    public Movie getMovie() {
        return movie;
    }
    
    public Action getChosenAction() {
        return chosenAction;
    }
    
    public void setChosenAction(Action chosenAction) {
        this.chosenAction = chosenAction;
    }
}

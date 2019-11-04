package com.screesh.choosinghelper;

import com.screesh.model.Movie;

import java.util.List;

public interface ChoiceMaker {
    /**
     *
     * @param toBeExcluded
     * @return
     */
    boolean confirmExclusion(List<Movie> toBeExcluded);

    /**
     *
     * @param toChoose
     * @return the index of the movie to exclude
     */
    int chooseOneToExclude(List<Movie> toChoose);


}

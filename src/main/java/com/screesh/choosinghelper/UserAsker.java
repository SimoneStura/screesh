package com.screesh.choosinghelper;

import com.screesh.model.Movie;

import java.util.List;

public interface UserAsker {
    /**
     *
     * @param toBeExcluded
     * @return
     */
    boolean confirmExclusion(Movie toBeExcluded);

    /**
     *
     * @param toChoose
     * @return the index of the movie to exclude
     */
    int chooseOneToExclude(List<MovieCounting> toChoose);


}

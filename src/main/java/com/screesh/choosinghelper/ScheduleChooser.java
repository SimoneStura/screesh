package com.screesh.choosinghelper;

import com.screesh.model.Movie;
import com.screesh.model.Screening;

import java.util.*;

public class ScheduleChooser {
    private ChoiceMaker choiceMaker;
    private Set<Movie> allMovies;
    
    public ScheduleChooser(HashSet<Movie> allMovies, ChoiceMaker choiceMaker) {
        this.allMovies = allMovies;
        this.choiceMaker = choiceMaker;
    }
    
    /**
     * @param solutions All the best solutions found
     * @return the collection of all the screenings which are included in a best solution after the choosing
     */
    public List<ChoiceMade> chooseSolution(List<SortedSet<Screening>> solutions) {
        assert solutions != null;
        assert solutions.size() > 0;
        
        List<HashSet<Movie>> solutionsForMovies = buildSolutionsForMovies(solutions);
    
        List<ChoiceMade> choices = new ArrayList<>();
        for (Movie movie : allMovies) {
            choices.add(new ChoiceMade(movie));
        }
        
        PriorityQueue<MovieCounting> mostExcludedMovies = new PriorityQueue<>(allMovies.size());
        for (Movie movie : allMovies) {
            MovieCounting exclusionsCounter = new MovieCounting(movie);
            for (HashSet<Movie> moviesInSolution : solutionsForMovies) {
                if(!moviesInSolution.contains(movie))
                    exclusionsCounter.counting++;
            }
            mostExcludedMovies.add(exclusionsCounter);
        }
        
        return choices;
    }
    
    private List<HashSet<Movie>> buildSolutionsForMovies(List<SortedSet<Screening>> solutions) {
        ArrayList<HashSet<Movie>> solutionsForMovies = new ArrayList<>(solutions.size());
        
        for (SortedSet<Screening> sol : solutions) {
            HashSet<Movie> moviesInSolution = new HashSet<>();
            for (Screening screen : sol)
                moviesInSolution.add(screen.getScreened());
            solutionsForMovies.add(moviesInSolution);
        }
        
        return solutionsForMovies;
    }
    
    private static class MovieCounting implements Comparable<MovieCounting> {
        Movie value;
        int counting;
        
        MovieCounting(Movie value) {
            this.value = value;
            counting = 0;
        }
        
        public int compareTo(MovieCounting o) {
            return Integer.compare(this.counting, o.counting);
        }
    }
}

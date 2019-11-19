package com.screesh.choosinghelper;

import com.screesh.model.Movie;
import com.screesh.model.Screening;
import org.junit.Assert;

import java.util.*;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class ScheduleChooser {
    private UserAsker userAsker;
    private Set<Movie> allMovies;
    
    public ScheduleChooser(HashSet<Movie> allMovies, UserAsker userAsker) {
        this.allMovies = allMovies;
        this.userAsker = userAsker;
    }
    
    /**
     * @param solutions All the best solutions found
     * @return the collection of all the screenings which are included in a best solution after the choosing
     */
    public List<ChoiceMade> chooseSolution(List<SortedSet<Screening>> solutions) {
        assert solutions != null;
        assert solutions.size() > 0;
        
        List<HashSet<Movie>> solutionsForMovies = buildSolutionsForMovies(solutions);
        
        PriorityQueue<MovieCounting> mostExcludedMovies = new PriorityQueue<>(allMovies.size(), Comparator.reverseOrder());
        for (Movie movie : allMovies) {
            int counting = 0;
            for (HashSet<Movie> moviesInSolution : solutionsForMovies) {
                if(!moviesInSolution.contains(movie))
                    counting++;
            }
            MovieCounting exclusionsCounter = new MovieCounting(movie, counting);
            mostExcludedMovies.add(exclusionsCounter);
        }

        if(mostExcludedMovies.size() == 0 || mostExcludedMovies.peek().getCounting() == 0)
            return Collections.emptyList();
    
        List<ChoiceMade> choices = new ArrayList<>();
        ArrayList<MovieCounting> notAlwaysExcluded = new ArrayList<>();
        boolean mustRepeatSolutionFinding = false;
        while (!mostExcludedMovies.isEmpty() && !mustRepeatSolutionFinding) {
            MovieCounting excludedMovie = mostExcludedMovies.poll();
            
            Assert.assertTrue(excludedMovie.getCounting() <= solutionsForMovies.size());
            if(excludedMovie.getCounting() == solutionsForMovies.size()) {
                //ask user if exclude or keep this movie
                boolean excluded = userAsker.confirmExclusion(excludedMovie.getValue());
                ChoiceMade currentChoice = new ChoiceMade(excludedMovie.getValue(), excluded);
                choices.add(currentChoice);
                mustRepeatSolutionFinding = !excluded;
            } else {
                notAlwaysExcluded.add(excludedMovie);
            }
        }
        
        if(notAlwaysExcluded.size() > 0 && notAlwaysExcluded.get(0).getCounting() > 0) {
            int indexToExclude = userAsker.chooseOneToExclude(notAlwaysExcluded);
            if(indexToExclude >= 0 && indexToExclude < notAlwaysExcluded.size()) {
                ChoiceMade choice = new ChoiceMade(notAlwaysExcluded.get(indexToExclude).getValue(), true);
                choices.add(choice);
            }
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
    
}

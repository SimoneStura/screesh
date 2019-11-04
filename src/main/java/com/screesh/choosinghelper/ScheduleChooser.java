package com.screesh.choosinghelper;

import com.screesh.model.Movie;
import com.screesh.model.Screening;
import org.junit.Assert;

import java.util.*;

import static org.hamcrest.Matchers.*;

public class ScheduleChooser {
    private ChoiceMaker choiceMaker;
    private HashSet<Movie> allMovies;

    public ScheduleChooser(HashSet<Movie> allMovies, ChoiceMaker choiceMaker) {
        this.allMovies = allMovies;
        this.choiceMaker = choiceMaker;
    }
    
    /**
     *
     * @param solutions All the best solutions found
     * @return the collection of all the screenings which are included in a best solution after the choosing
     */
    public SortedSet<Screening> chooseSolution(List<SortedSet<Screening>> solutions) {
        Assert.assertNotNull(solutions);
        Assert.assertTrue(solutions.size() > 0);
        PriorityQueue<MovieCounting> exclusionCount = new PriorityQueue<>();
        for(SortedSet<Screening> sol : solutions) {
        
        }

        return null;
    }
    
    private class MovieCounting implements Comparable<MovieCounting> {
        Movie value;
        int counting;
        
        MovieCounting(Movie value) {
            this.value = value;
            counting = 0;
        }
        
        public int compareTo(MovieCounting o) {
            return Integer.compare(o.counting, this.counting);
        }
    }
}

package com.screesh;

import com.screesh.choosinghelper.ChoiceMade;
import com.screesh.choosinghelper.ScheduleChooser;
import com.screesh.console.ChooseWithTerminal;
import com.screesh.console.ScreeningsImport;
import com.screesh.console.SolutionPrinter;
import com.screesh.model.FilmFestival;
import com.screesh.model.Movie;
import com.screesh.model.Screening;
import com.screesh.solver.ConflictsSolver;

import java.io.IOException;
import java.util.*;

public class Application {
    //TODO: DELETE this fucking mess, it is TEMPORARY
    public static void main(String[] args) throws IOException {
        String filepath = "src/main/resources/festival_input.json";
        FilmFestival ff = ScreeningsImport.buildFromJson(filepath);
        
        HashSet<Movie> conflictualMovies = ff.getMovies();
        ConflictsSolver<Screening> conflictsSolver = new ConflictsSolver<>();
        for (Movie conflMovie : conflictualMovies) {
            List<Screening> showsForMovie = ff.getScreens(conflMovie);
            conflictsSolver.addGroup(showsForMovie);
        }
        
        List<Movie> moviesMustIncluded = new ArrayList<>();
        List<SortedSet<Screening>> goodSolutions = null;
        while (goodSolutions == null) {
            for (Movie mustIncluded : moviesMustIncluded) {
                List<Screening> showsForMovie = ff.getScreens(mustIncluded);
                if (showsForMovie.size() == 1)
                    conflictsSolver.mustInclude(showsForMovie.get(0));
                else if (showsForMovie.size() > 0)
                    conflictsSolver.mustIncludeOne(showsForMovie);
            }
            goodSolutions = conflictsSolver.allGoodSolutions();
            ScheduleChooser chooser = new ScheduleChooser(conflictualMovies, new ChooseWithTerminal());
            List<ChoiceMade> newchoices = chooser.chooseSolution(goodSolutions);
            if(newchoices.isEmpty())
                break;
            for(ChoiceMade choice : newchoices) {
                if(choice.isExcluded()) {
                    List<Screening> showsToExclude = ff.getScreens(choice.getMovie());
                    conflictsSolver.remove(showsToExclude);
                } else {
                    List<Screening> shows = ff.getScreens(choice.getMovie());
                    if(shows.size() == 1)
                        conflictsSolver.mustInclude(shows.get(0));
                    else
                        conflictsSolver.mustIncludeOne(shows);
                }
            }
        }
        
        for (SortedSet<Screening> finalSolution : goodSolutions)
            SolutionPrinter.printSolution(finalSolution, ff.getName());
    }
}

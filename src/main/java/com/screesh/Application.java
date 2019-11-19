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
        String filepath = "src/main/resources/tff_37.json";
        FilmFestival ff = ScreeningsImport.buildFromJson(filepath);
        
        HashSet<Movie> currentSolutionMovies = new HashSet<>();
        ConflictsSolver<Screening> solver = new ConflictsSolver<>();
        List<SortedSet<Screening>> goodSolutions = new ArrayList<>();
        
        for (Integer currentPriority : ff.getPriorities()) {
            HashSet<Movie> currentPriorityMovies = ff.getMoviesWithPriority(currentPriority);
            for (Movie conflMovie : currentPriorityMovies) {
                List<Screening> showsForMovie = ff.getScreens(conflMovie);
                solver.addGroup(showsForMovie);
            }
            
            currentSolutionMovies.addAll(currentPriorityMovies);
            
            while (goodSolutions.size() == 0 || goodSolutions.get(0).size() < currentSolutionMovies.size()) {
                goodSolutions = solver.allGoodSolutions();
                
                ScheduleChooser chooser = new ScheduleChooser(currentSolutionMovies, new ChooseWithTerminal());
                List<ChoiceMade> newChoices = chooser.chooseSolution(goodSolutions);
                if (newChoices.isEmpty())
                    continue;
                for (ChoiceMade choice : newChoices) {
                    if (choice.isExcluded()) {
                        currentSolutionMovies.remove(choice.getMovie());
                        List<Screening> showsToExclude = ff.getScreens(choice.getMovie());
                        solver.remove(showsToExclude);
                    } else {
                        List<Screening> shows = ff.getScreens(choice.getMovie());
                        if (shows.size() == 1)
                            solver.mustInclude(shows.get(0));
                        else
                            solver.mustIncludeOne(shows);
                    }
                    goodSolutions.clear();
                }
            }
        }
    
    
        for (int i = 0; i < goodSolutions.size(); i++) {
            SortedSet<Screening> finalSolution = goodSolutions.get(i);
            SolutionPrinter.printSolution(finalSolution, ff.getName() + " - SOLUZIONE " + (i + 1));
        }
    }
}

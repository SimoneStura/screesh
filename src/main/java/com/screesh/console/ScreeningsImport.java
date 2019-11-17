package com.screesh.console;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.screesh.model.Cinema;
import com.screesh.model.FilmFestival;
import com.screesh.model.Movie;
import com.screesh.model.Screening;
import com.screesh.model.dto.FestivalSelectionDto;
import com.screesh.model.dto.MovieDto;
import com.screesh.model.dto.ScreeningDto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ScreeningsImport {
    
    private static final Cinema mas = new Cinema("MAS");
    private static final Cinema rep = new Cinema("REP");
    private static final int defualtMinutesForHosted = 10;
    
    public static FilmFestival buildFromJson(String filepath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FestivalSelectionDto festivalDto = mapper.readValue(new File(filepath), FestivalSelectionDto.class);
        
        return buildFilmFestival(festivalDto);
    }
    
    private static FilmFestival buildFilmFestival(FestivalSelectionDto festivalDto) {
        FilmFestival ff = new FilmFestival(festivalDto.getFestivalName(), festivalDto.getMinimumToWaitInMinutes());
        insertCinemas(ff);
        
        for(MovieDto movieDto : festivalDto.getMovies()) {
            Movie m = buildMovie(movieDto);
            
            ArrayList<Screening> screenings = new ArrayList<>();
            for(ScreeningDto screeningDto : movieDto.getScreenings()) {
                Screening s = buildScreening(m, screeningDto, movieDto.getPriority(), ff.getMinimumToWaitInMinutes());
                screenings.add(s);
            }
            ff.addMovie(m);
            ff.addScreenings(screenings);
        }
        return ff;
    }
    
    private static void insertCinemas(FilmFestival ff) {
        ff.addCinema(mas);
        ff.addCinema(rep);
        ff.setDistanceBetweenCinemas(mas.getName(), rep.getName(), 15);
    }
    
    private static Movie buildMovie(MovieDto movieDto) {
        Movie m = new Movie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRuntime());
        return m;
    }
    
    private static Screening buildScreening(Movie movie, ScreeningDto screeningDto, int priority, int minutesToWait) {
        Cinema cinema = null;
        switch(screeningDto.getCinema()) {
            case "MAS":
                cinema = mas;
                break;
            case "REP":
                cinema = rep;
                break;
        }
        Screening s = new Screening(movie, screeningDto.getStartTime(), cinema, String.valueOf(screeningDto.getTheater()));
        s.setPriority(priority);
        s.setRegularPauseInMinutes(minutesToWait);
        if(screeningDto.isHosted())
            s.setAdditionalMinutes(defualtMinutesForHosted);
        
        return s;
    }
}

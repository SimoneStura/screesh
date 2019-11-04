package com.screesh.model.dto;

import java.util.List;

public class FestivalSelectionDto {
    private String festivalName;
    private int minimumToWaitInMinutes;
    private List<MovieDto> movies;
    
    public String getFestivalName() {
        return festivalName;
    }
    
    public void setFestivalName(String festivalName) {
        this.festivalName = festivalName;
    }
    
    public int getMinimumToWaitInMinutes() {
        return minimumToWaitInMinutes;
    }
    
    public void setMinimumToWaitInMinutes(int minimumToWaitInMinutes) {
        this.minimumToWaitInMinutes = minimumToWaitInMinutes;
    }

    public List<MovieDto> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDto> movies) {
        this.movies = movies;
    }
}

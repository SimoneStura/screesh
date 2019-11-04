package com.screesh.model;

import com.screesh.solver.PlacedOverTime;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Objects;

public class Screening implements PlacedOverTime<Screening> {
    private Movie screened;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Cinema cinema;
    private String theater;
    
    //TODO: spostare queste info dove compete, non bisogna sovraccaricare una classe del model di responsabilità
    private int priority;
    private int minutesToWait;
    
    public Screening(Movie screened, LocalDateTime startTime, Cinema cinema, String theater) {
        this.screened = screened;
        this.startTime = startTime;
        endTime = startTime.plusMinutes(screened.getRuntime());
        this.cinema = cinema;
        this.theater = theater;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Screening)) return false;
        Screening screening = (Screening) o;
        return screened.equals(screening.screened) &&
                startTime.equals(screening.startTime) &&
                Objects.equals(cinema, screening.cinema) &&
                Objects.equals(theater, screening.theater);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(screened, startTime, cinema, theater);
    }
    
    public Movie getScreened() {
        return screened;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public Cinema getCinema() {
        return cinema;
    }
    
    public String getTheater() {
        return theater;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public int getMinutesToWait() {
        return minutesToWait;
    }
    
    public void setMinutesToWait(int minutesToWait) {
        this.minutesToWait = minutesToWait;
    }
    
    @Override
    public int compareTo(Screening s) {
        int cmp = startTime.compareTo(s.startTime);
        
        if (cmp == 0)
            cmp = endTime.compareTo(s.endTime);
        if (cmp == 0)
            cmp = screened.compareTo(s.screened);
        if (cmp == 0)
            cmp = cinema.compareTo(s.cinema);
        if (cmp == 0)
            cmp = theater.compareToIgnoreCase(s.theater);
        
        return cmp;
    }
    
    @Override
    public boolean isInConflictWith(Screening other) {
        boolean ctrl = this.startTime.compareTo(other.endTime) <= 0;
        if (ctrl) {
            ctrl = this.endTime.compareTo(other.startTime) >= 0;
        } else {
        
        }
        
        return ctrl;
    }
    
    @Override
    public Period gap(Screening element) {
        return null;
    }
    
    @Override
    public boolean sameDayAs(Screening element) {
        return false;
    }
}

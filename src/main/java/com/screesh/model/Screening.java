package com.screesh.model;

import com.screesh.solver.PlacedOverTime;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Screening implements PlacedOverTime<Screening> {
    private Movie screened;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Cinema cinema;
    private String theater;
    private int priority;
    
    //TODO: spostare queste info dove compete, non bisogna sovraccaricare una classe del model di responsabilità
    private int additionalMinutes;
    private int regularPauseInMinutes;
    
    public Screening(Movie screened, LocalDateTime startTime, Cinema cinema, String theater) {
        this.screened = screened;
        this.startTime = startTime;
        endTime = calculateEndTime(screened, startTime);
        this.cinema = cinema;
        this.theater = theater;
        regularPauseInMinutes = 0;
        additionalMinutes = 0;
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
    
    public int getAdditionalMinutes() {
        return additionalMinutes;
    }
    
    public void setAdditionalMinutes(int additionalMinutes) {
        this.additionalMinutes = additionalMinutes;
    }
    
    public int getRegularPauseInMinutes() {
        return regularPauseInMinutes;
    }
    
    public void setRegularPauseInMinutes(int regularPauseInMinutes) {
        this.regularPauseInMinutes = regularPauseInMinutes;
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
    
    //TODO: centralizzare dove compete
    @Override
    public boolean isInConflictWith(Screening that) {
        Duration gapBetween = this.gap(that);
        if(gapBetween == null)
            return true;
        if(gapBetween.isNegative()) {
            gapBetween = gapBetween.negated()
                    .minusMinutes(that.additionalMinutes);
        } else {
            gapBetween = gapBetween.minusMinutes(this.additionalMinutes);
        }
        
        int minutesForTripAndQueue = this.regularPauseInMinutes + this.cinema.getDistance(that.cinema);
        gapBetween = gapBetween.minusMinutes(minutesForTripAndQueue);
        
        return gapBetween.isNegative();
    }
    
    @Override
    public Duration gap(Screening that) {
        Duration result = null;
        
        if (startTime.isBefore(that.startTime)) {
            if (endTime.isBefore(that.startTime) || endTime.isEqual(that.startTime))
                result = Duration.between(endTime, that.startTime);
        } else if (startTime.isAfter(that.startTime)) {
            if (startTime.isAfter(that.endTime) || startTime.isEqual(that.endTime))
                result = Duration.between(startTime, that.endTime);
        }
        // if startTime of the 2 objects are equals, return null
        
        return result;
    }
    
    @Override
    public boolean sameDayAs(Screening that) {
        return this.startTime.toLocalDate().isEqual(that.startTime.toLocalDate());
    }
    
    private LocalDateTime calculateEndTime(Movie screened, LocalDateTime startTime) {
        return startTime.plusMinutes(screened.getRuntime());
    }
}
